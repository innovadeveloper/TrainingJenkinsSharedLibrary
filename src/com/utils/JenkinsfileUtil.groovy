#!/usr/bin/env groovy
package com.utils

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.utils.JenkinsProperty

@Grab('com.fasterxml.jackson.core:jackson-databind:2.12.5')
@Grab('com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.12.5')

class JenkinsfileUtil {

    // Ejemplo de acceso a variables definidas en el script del pipeline
    static def getProjectName() {
        return "azar"
    }

    static String main(String yamlContent) {
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
}