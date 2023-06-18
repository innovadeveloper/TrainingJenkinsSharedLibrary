import groovy.json.JsonSlurper

class MyLibrary {
    static def readResource(String resourceName) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceName)
        if (inputStream) {
            def content = inputStream.text
            println "Contenido del recurso ${resourceName}: ${content}"
            return content
        } else {
            println "No se pudo encontrar el recurso ${resourceName}"
            return null
        }
    }
}