import groovy.json.JsonSlurper

class MyLibrary {
    String readResourceFile() {
//        def resourceStream = getClass().getResourceAsStream("/my-resource-file.txt")
        def resourceContent = libraryResource('my-resource-file.txt')
//        def resourceContent = resourceStream?.text
        return resourceContent
    }
}