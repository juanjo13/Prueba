package mx.gob.fgjez.gebci.geo

class Procedencia {

    String id
    String estado
    String descripcion

    static constraints = {
    }

    static mapping = {
        table schema:'geo'
        id generator: 'assigned', sqlType: 'char', length: 5
        estado sqlType: 'char', length: 2
        version false
    }
}
