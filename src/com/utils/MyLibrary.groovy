//import groovy.json.JsonSlurper
//import com.cloudbees.groovy.cps.sandbox.SandboxInvoker

class MyLibrary {
    String readResourceFile() {
//        def resourceStream = getClass().getResourceAsStream("/my-resource-file.txt")
//        def resourceContent = libraryResource('my-resource-file.txt')
//        def resourceContent = SandboxInvoker.libraryResource('my-resource-file.txt')
        def resourceStream = getClass().getResourceAsStream('/my-resource-file.txt')

//        def resourceContent = resourceStream?.text
        return resourceStream
    }
}