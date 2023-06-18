#!/usr/bin/env groovy
package com.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import sharedlibrary.models.JenkinsProperty

@Grab('com.fasterxml.jackson.core:jackson-databind:2.12.5')
@Grab('com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.12.5')

class JenkinsfileUtil {

    // Ejemplo de acceso a variables definidas en el script del pipeline
    static def getProjectName() {
        return "azar"
    }

    static void main() {
        def yamlContent = myLibrary.readAsResource("templates/vars/template.dev.yaml") // Reemplaza con la ruta de tu archivo YAML
//        def objectMapper = new ObjectMapper(new YAMLFactory())
//        def jenkinsProperty = objectMapper.readValue(yamlContent, JenkinsProperty)
//        def dataText = generateKubernetesResources(jenkinsProperty, true)
//        dataText.collect(){
//            def rootPath = "/Users/kennybaltazaralanoca/Projects/GroovyProjects/JenkinsSharedLibrary/output/"
//            def file = new File(rootPath + it.key)
//            file.write(it.value)
//        }
        return yamlContent
    }
}