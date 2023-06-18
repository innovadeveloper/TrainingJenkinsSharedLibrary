import groovy.json.JsonSlurper

class MyLibrary {
    static String readResource(String resourceName) {
        def classLoader = getClass().getClassLoader()
        def resourceStream = classLoader.getResourceAsStream(resourceName)

        if (resourceStream) {
            return resourceStream.text
        } else {
            throw new FileNotFoundException("Resource not found: ${resourceName}")
        }
    }
}