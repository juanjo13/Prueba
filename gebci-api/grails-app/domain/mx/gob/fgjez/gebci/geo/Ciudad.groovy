package mx.gob.fgjez.gebci.geo

class Ciudad {

    String id
    String clave
    String nombre

    static belongsTo = [
            estado: Estado
    ]

    static constraints = {
        clave blank: false
        nombre blank: false
    }

    static mapping = {
        table schema: 'geo'

        id generator: 'assigned', sqlType: 'char', length: 4
        clave sqlType: 'char', length: 2
        nombre length: 70

        version false
    }
}
