//import groovy.json.JsonSlurper
//import com.cloudbees.groovy.cps.sandbox.SandboxInvoker
import java.io.InputStream
import com.cloudbees.groovy.cps.sandbox.SandboxInvoker

class MyLibrary {
    def pathFile = 'test.txt'
    String readAsInputStream() {
        def myResource = this.class.getResourceAsStream(pathFile)
        return myResource
    }
    String readAsInputStreamTwo() {
        def myResource = this.getClass().getClassLoader().getResourceAsStream(pathFile)
        return myResource
    }

    String readAsResource() {
        def myResource = SandboxInvoker.libraryResource(pathFile)
        return myResource
    }

}