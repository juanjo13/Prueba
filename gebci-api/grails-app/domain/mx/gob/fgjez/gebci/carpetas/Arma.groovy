package mx.gob.fgjez.gebci.carpetas

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes = ['id', 'tipo', 'calibri', 'marca', 'modelo', 'matricula'])
@ToString(includes = ['id', 'tipo', 'calibri', 'marca', 'modelo', 'matricula'], includeNames = true, includePackage = false)
class Arma extends Objeto {

    String tipo
    String calibre
    String marca
    String modelo
    String matricula

    static constraints = {
        importFrom Objeto

        tipo nullable: true
        calibre nullable: true
        marca nullable: true
        modelo nullable: true
        matricula nullable: true
    }

    static mapping = {
        tipo length: 255
        calibre length: 255
        marca length: 255
        modelo length: 255
        matricula length: 255
    }

    @Override
    String getTipoObjeto() { 'arma' }
}
