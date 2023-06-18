//import groovy.json.JsonSlurper
//import com.cloudbees.groovy.cps.sandbox.SandboxInvoker
import java.io.InputStream
import com.cloudbees.groovy.cps.sandbox.SandboxInvoker

String simpleMessage() {
    return 'hola'
}
String readAsInputStream() {
    def myResource = this.class.getResourceAsStream('test.txt')
    return myResource
}
String readAsInputStreamTwo() {
    def myResource = this.getClass().getClassLoader().getResourceAsStream('test.txt')
    return myResource
}

String readAsResource(filePath) {
    def myResource = libraryResource(filePath)
    return myResource
}