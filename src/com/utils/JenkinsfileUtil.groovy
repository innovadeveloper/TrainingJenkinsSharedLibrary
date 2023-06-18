#!/usr/bin/env groovy
package com.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.utils.JenkinsProperty

@Grab('com.fasterxml.jackson.core:jackson-databind:2.12.5')
@Grab('com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.12.5')

class JenkinsfileUtil {
    def readClosure
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


    String readByClosure2(def closure){
        this.readClosure = closure
        def yamlContent = this.readClosure('templates/vars/template.dev.yaml') // Reemplaza con la ruta de tu archivo YAML
        def objectMapper = new ObjectMapper(new YAMLFactory())
        JenkinsProperty jenkinsProperty = objectMapper.readValue(yamlContent, JenkinsProperty)
        return jenkinsProperty.project.project_info.app_name
    }
}