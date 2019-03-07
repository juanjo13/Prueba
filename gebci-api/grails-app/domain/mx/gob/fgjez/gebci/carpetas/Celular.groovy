package mx.gob.fgjez.gebci.carpetas

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import net.kaleidos.hibernate.usertype.ArrayType

@EqualsAndHashCode(includes = ['id', 'marca', 'modelo', 'descripcion', 'imei', 'rol', 'estatus'])
@ToString(includes = ['id', 'marca', 'modelo', 'descripcion', 'imei', 'rol', 'estatus'], includeNames = true, includePackage = false)
class Celular extends Objeto {

    String[] imei
    String marca
    String modelo
    String descripcion

    static constraints = {
        importFrom Objeto

        imei nullable: true
        marca nullable: true
        modelo nullable: true
        descripcion nullable: true
    }

    static mapping = {
        imei type: ArrayType, params: [type: String]
        marca length: 255
        modelo length: 255
        descripcion length: 255
    }

    @Override
    String getTipoObjeto() { 'celular' }
}
