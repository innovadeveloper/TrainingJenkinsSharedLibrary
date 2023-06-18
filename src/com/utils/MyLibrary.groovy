import groovy.json.JsonSlurper

class MyLibrary {
    static String readResourceFile() {
        def resourceStream = this.class.classLoader.getResourceAsStream("my-resource-file.txt")
        def resourceContent = resourceStream?.text
        return resourceContent
    }
}