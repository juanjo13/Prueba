package mx.gob.fgjez.gebci.carpetas

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes = ['id', 'marca', 'linea', 'modelo', 'color', 'numeroSerie'])
@ToString(includes = ['id', 'marca', 'linea', 'modelo', 'color', 'numeroSerie'], includeNames = true, includePackage = false)
class Vehiculo extends Objeto {

    String marca
    String linea
    String modelo
    String color
    String numeroSerie

    static constraints = {
        importFrom Objeto

        marca nullable: true
        linea nullable: true
        modelo nullable: true
        color nullable: true
        numeroSerie nullable: true
    }

    static mapping = {
        marca length: 255
        linea length: 255
        modelo length: 10
        color length: 255
        numeroSerie length: 255
    }

    @Override
    String getTipoObjeto() { 'vehiculo' }
}
