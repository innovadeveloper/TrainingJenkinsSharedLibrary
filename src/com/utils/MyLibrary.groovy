//import groovy.json.JsonSlurper
//import com.cloudbees.groovy.cps.sandbox.SandboxInvoker
import java.io.InputStream
import com.cloudbees.groovy.cps.sandbox.SandboxInvoker

class MyLibrary {
    String readAsInputStream() {
        def myResource = this.class.getResourceAsStream('/my-resource-file.txt')
        return myResource
    }
    String readAsInputStreamTwo() {
        def myResource = this.getClass().getClassLoader().getResourceAsStream('my-resource-file.txt')
        return myResource
    }

    String readAsResource() {
        def myResource = SandboxInvoker.libraryResource('my-resource-file.txt')
        return myResource
    }

}