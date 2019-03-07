package mx.gob.fgjez.gebci.geo

class Asentamiento {

    String id
    String clave
    String nombre
    String codigoPostal
    String tipo
    String zona

    static belongsTo = [
            estado: Estado,
            municipio: Municipio,
            ciudad: Ciudad
    ]

    static constraints = {
        clave blank: false
        nombre blank: false
        codigoPostal blank: false
        ciudad nullable: true
        tipo blank: false
        zona blank: false
    }

    static mapping = {
        table schema: 'geo'

        id generator: 'assigned', sqlType: 'char', length: 9
        clave sqlType: 'char', length: 4
        nombre length: 90
        codigoPostal sqlType: 'char', length: 5

        version false
    }
}
