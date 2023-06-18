import groovy.json.JsonSlurper

class MyLibrary {
    String readResourceFile() {
        def resourceStream = this.class.classLoader.getResourceAsStream("my-resource-file.txt")
        def resourceContent = resourceStream?.text
        return resourceContent
    }
}