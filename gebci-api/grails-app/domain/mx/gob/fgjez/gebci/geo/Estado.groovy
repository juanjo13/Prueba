package mx.gob.fgjez.gebci.geo

class Estado {

    String id
    String clave
    String nombre
    String abreviatura

    static hasMany = [
            municipios: Municipio,
            ciudades: Ciudad
    ]

    static constraints = {
        clave blank: false
        nombre blank: false
        abreviatura blank: false
    }

    static mapping = {
        table schema: 'geo'

        id generator: 'assigned', sqlType: 'char', length: 2
        clave sqlType: 'char', length: 2
        nombre length: 50
        abreviatura length: 10

        version false
    }
}
