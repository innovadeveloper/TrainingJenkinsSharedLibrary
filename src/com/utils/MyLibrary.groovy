import groovy.json.JsonSlurper

class MyLibrary {
    String readResourceFile() {
        def resourceStream = this.class.classLoader.getResourceAsStream("resources/my-resource-file.txt")
        def resourceContent = resourceStream?.text
        return resourceContent
    }
}