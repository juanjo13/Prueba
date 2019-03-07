package mx.gob.fgjez.gebci.geo

class Municipio {

    String id
    String clave
    String nombre

    static belongsTo = [
            estado: Estado
    ]

    static hasMany = [
            asentamientos: Asentamiento
    ]

    static constraints = {
        clave blank: false
        nombre blank: false
    }

    static mapping = {
        table schema: 'geo'

        id generator: 'assigned', sqlType: 'char', length: 5
        clave sqlType: 'char', length: 2
        nombre length: 90

        version false
    }
}
