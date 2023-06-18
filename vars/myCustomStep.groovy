package sharedlibrary
@Grab('com.fasterxml.jackson.core:jackson-databind:2.12.5')
import com.fasterxml.jackson.databind.ObjectMapper

String myFunction() {
    // Crear un objeto ObjectMapper
    def objectMapper = new ObjectMapper()

    // Crear un objeto de ejemplo
    def exampleObject = [name: 'John', age: 30]

    // Convertir el objeto a formato JSON
    def json = objectMapper.writeValueAsString(exampleObject)

    // Imprimir el JSON
    println "JSON: $json"
    return json
}

def myCustomStep() {
    // Lógica personalizada de tu paso
    echo 'Este es un paso personalizado ejecutado desde la librería compartida'
}
