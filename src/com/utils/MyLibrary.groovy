import groovy.json.JsonSlurper

class MyLibrary {
    public static void main(String[] args) {
        readResource("my-resource-file.txt")
    }
    static def readResource(String resourceName) {
        def classLoader = getClass().getClassLoader()
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