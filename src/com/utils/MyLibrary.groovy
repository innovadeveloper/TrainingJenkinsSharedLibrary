import groovy.json.JsonSlurper

class MyLibrary {
    static def readResource(String resourceName) {
        ClassLoader classLoader = ClassLoader.systemClassLoader
        InputStream inputStream = classLoader.getResourceAsStream(resourceName)
        def content = null
        if (inputStream) {
            content = inputStream.text
            println "Contenido del recurso ${resourceName}: ${content}"
        } else {
            println "No se pudo encontrar el recurso ${resourceName}"
        }
        return content
    }
}