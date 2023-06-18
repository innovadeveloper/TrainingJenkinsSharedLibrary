#!/usr/bin/env groovy
package com.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.utils.JenkinsProperty
import org.apache.commons.lang.StringUtils

@Grab('com.fasterxml.jackson.core:jackson-databind:2.12.5')
@Grab('com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.12.5')

class JenkinsfileUtil {
    def getContentFromPath
    // Ejemplo de acceso a variables definidas en el script del pipeline
    static def getProjectName() {
        return "azar"
    }

    static String main(String readClosure) {
//        JenkinsfileUtil.readClosure = readClosure
        def yamlContent = readClosure('templates/vars/template.dev.yaml') // Reemplaza con la ruta de tu archivo YAML
//        def yamlContent =content // Reemplaza con la ruta de tu archivo YAML
        def objectMapper = new ObjectMapper(new YAMLFactory())
        JenkinsProperty jenkinsProperty = objectMapper.readValue(yamlContent, JenkinsProperty)
//        def dataText = generateKubernetesResources(jenkinsProperty, true)
//        dataText.collect(){
//            def rootPath = "/Users/kennybaltazaralanoca/Projects/GroovyProjects/JenkinsSharedLibrary/output/"
//            def file = new File(rootPath + it.key)
//            file.write(it.value)
//        }
        return jenkinsProperty.project.project_info.app_name
    }

    static String readByClosure(def closure){
        return closure('test.txt')
    }


    def readByClosure2(def closure){
        this.getContentFromPath = closure
        def yamlContent = this.getContentFromPath('templates/vars/template.dev.yaml') // Reemplaza con la ruta de tu archivo YAML
        def objectMapper = new ObjectMapper(new YAMLFactory())
        JenkinsProperty jenkinsProperty = objectMapper.readValue(yamlContent, JenkinsProperty)
        def dataText = this.generateKubernetesResources(jenkinsProperty, true)
        def fileTextCreated = ""
        dataText.collect(){
            sh 'pwd'
            def rootPath = "./output/"
            def file = new File(rootPath + it.key)
            file.write(it.value)
            fileTextCreated = file.text
        }
//
        return fileTextCreated
    }


    def generateKubernetesResources(JenkinsProperty jenkinsProperty, boolean isSingleFile = false) {
        def pvTemplate = this.getContentFromPath("templates/kubernetes/template.pv.yaml")
        def pvcTemplate = this.getContentFromPath("templates/kubernetes/template.pvc.yaml")
        def deploymentTemplate = this.getContentFromPath("templates/kubernetes/template.deployment.yaml")
        def serviceTemplate = this.getContentFromPath("templates/kubernetes/template.service.yaml")
        def EMPTY_12_SPACE = StringUtils.repeat(' ', 12)
        def EMPTY_14_SPACE = StringUtils.repeat(' ', 14)
        def EMPTY_6_SPACE = StringUtils.repeat(' ', 6)
        def EMPTY_4_SPACE = StringUtils.repeat(' ', 4)
        def isStorageDefined = (jenkinsProperty.storage != null)
//        def isImageDefined = (jenkinsProperty.project.repository_info.registry_image != null)
        def isCredentialsIdDefined = (jenkinsProperty.project.repository_info.registry_credentials_id != null)
        def registryImageWithVersion = ("${jenkinsProperty.project.repository_info.registry_image}:latest") // TODO CAMBIAR EL LATEST POR LA VERSION RECUPERADA DEL POM.XML O FICHERO DE CONFIGURACION (build.gradle, package.json)
        // Generar las definiciones de los puertos
        def portDefinitions = jenkinsProperty.service.ports.collect { port ->
            "- containerPort: ${port.port}"
        }.join("\n${EMPTY_12_SPACE}")

        def volumeMountsDefinitions = ''
        if(isStorageDefined){
            volumeMountsDefinitions = jenkinsProperty.storage.mount_path.collect { mountPath ->
                "- name: ${jenkinsProperty.project.project_info.app_name}-volume\n${EMPTY_14_SPACE}mountPath: ${mountPath}"
            }.join("\n${EMPTY_12_SPACE}")
        }

        def environmentVariablesDefinitions = jenkinsProperty.enviroment_variables.collect { environmentVariable ->
            "- name: ${environmentVariable.name}\n${EMPTY_14_SPACE}value: ${environmentVariable.value}"
        }.join("\n${EMPTY_12_SPACE}")

        def servicePortDefinitions = jenkinsProperty.service.ports.collect { port ->
            "- protocol: ${port.protocol}\n${EMPTY_6_SPACE}port: ${port.port}\n${EMPTY_6_SPACE}targetPort: ${port.targetPort}\n${EMPTY_6_SPACE}nodePort: ${port.nodePort}"
        }.join("\n${EMPTY_4_SPACE}")

        def pvDefinition = (!isStorageDefined) ? '': pvTemplate
                .replace('${YOUR_APP_NAME}', jenkinsProperty.project.project_info.app_name)
                .replace('${STORAGE_SIZE_PV}', jenkinsProperty.storage.volume_size)
        def pvcDefinition = (!isStorageDefined) ? '': pvcTemplate
                .replace('${YOUR_APP_NAME}', jenkinsProperty.project.project_info.app_name)
                .replace('${STORAGE_SIZE_PV}', jenkinsProperty.storage.volume_size)
        def deploymentDefinition = deploymentTemplate
                .replace('${YOUR_APP_NAME}', jenkinsProperty.project.project_info.app_name)
                .replace('${ENVIRONMENT_VARIABLES}', environmentVariablesDefinitions)
                .replace('${VOLUME_MOUNTS}', volumeMountsDefinitions)
                .replace('${YOUR_REPLICAS_APP}', jenkinsProperty.replicas.max.toString())
                .replace('${PORT_DEFINITIONS}', portDefinitions)

        def startMarker = '${START_VOLUMES}'
        def endMarker = '${END_VOLUMES}'

        if(!isStorageDefined)
        {
            deploymentDefinition = deploymentDefinition.replaceAll("(?s)\\Q$startMarker\\E.*?\\Q$endMarker\\E", "")
        }else{
            deploymentDefinition = deploymentDefinition
                    .replace(startMarker, "")
                    .replace(endMarker, "")
        }

        def startImagePullSecretMarker = '${START_IMAGE_PULL_SECRET}'
        def endImagePullSecretMarker = '${END_IMAGE_PULL_SECRET}'

        if(!isCredentialsIdDefined){
            deploymentDefinition = deploymentDefinition.replaceAll("(?s)\\Q$startImagePullSecretMarker\\E.*?\\Q$endImagePullSecretMarker\\E", "")
        }else{
            deploymentDefinition = deploymentDefinition
                    .replace(startImagePullSecretMarker, "")
                    .replace(endImagePullSecretMarker, "")
                    .replace('${CLIENT_SECRET_ID}', jenkinsProperty.project.repository_info.registry_credentials_id)
                    .replace('${YOUR_IMAGE_PATH}', registryImageWithVersion)
        }

        def serviceDefinition = serviceTemplate
                .replace('${YOUR_APP_NAME}', jenkinsProperty.project.project_info.app_name)
                .replace('${YOUR_SERVICE_PORTS}', servicePortDefinitions)
        if(isSingleFile){
            def allDefinitions = [pvDefinition, pvcDefinition, deploymentDefinition, serviceDefinition].join("\n---\n")
            if(!isStorageDefined){
                allDefinitions = [deploymentDefinition, serviceDefinition].join("\n---\n")
            }
            return ['single_definitions.yaml' : allDefinitions]
        }
        if(isStorageDefined){
            return ['pvDefinition.yaml' : pvDefinition, 'pvcDefinition.yaml' : pvcDefinition, 'deploymentDefinition.yaml' : deploymentDefinition, 'serviceDefinition.yaml' : serviceDefinition]
        }
        return ['deploymentDefinition.yaml' : deploymentDefinition, 'serviceDefinition.yaml' : serviceDefinition]
    }

}