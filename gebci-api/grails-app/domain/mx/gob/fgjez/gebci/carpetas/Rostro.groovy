package mx.gob.fgjez.gebci.carpetas

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes = ['id', 'fecha', 'archivo', 'posicion', 'descripcion'])
@ToString(includes = ['id', 'fecha', 'archivo', 'posicion', 'descripcion'], includeNames = true, includePackage = false)
class Rostro extends Objeto {

    Date fecha
    String archivo
    String posicion
    String descripcion

    static constraints = {
        importFrom Objeto

        fecha nullable: false
        archivo nullable: false
        posicion nullable: false
        descripcion nullable: false
    }

    static mapping = {
        fecha length: 10
        archivo length: 255
        posicion length: 255
        descripcion length: 255
    }

    @Override
    String getTipoObjeto() { 'rostro' }
}
