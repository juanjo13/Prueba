package mx.gob.fgjez.gebci.carpetas

import grails.databinding.BindUsing
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import mx.gob.fgjez.gebci.AccessControlledResource
import mx.gob.fgjez.gebci.abac.Permiso
import mx.gob.fgjez.gebci.catalog.Delito
import mx.gob.fgjez.gebci.geo.Procedencia

@EqualsAndHashCode(includes = ['id', 'numero', 'descripcion', 'fechaInicio', 'delito', 'procedencia', 'relato', 'estatus'])
@ToString(includes = ['id', 'numero', 'descripcion', 'fechaInicio', 'delito', 'procedencia', 'estatus'], includeNames = true, includePackage = false)
class Carpeta extends AccessControlledResource {

    String numero
    String descripcion
    Date fechaInicio
    Delito delito
    Procedencia procedencia
    String relato
    String estatus

    @BindUsing({ obj, source ->
        source['permisos'].collect { p  ->
            new Permiso(p << ['authKey': obj.id, 'carpeta': obj])
        }
    })
    Set<Permiso> permisos

    static hasMany = [permisos: Permiso]

    static constraints = {
        authKey nullable: true
        numero blank: false
        fechaInicio nullable: false
        descripcion blank: false
        estatus blank: false
    }

    static mapping = {
        version false
        id generator: 'sequence', params: [sequence: 'et_carpeta_id_seq']
        authKey updateable: false
        numero length: 32
        fechaInicio sqlType: 'date'
        delito column: 'delito', cascade: 'none'
        procedencia column: 'procedencia', cascade: 'none'
        relato sqlType: 'text'
        permisos cascade: 'all-delete-orphan', lazy: false
    }

    def getOwner() {
        permisos.find { p ->
            p.access == 'owner'
        }
    }

    def getExecutiveDelegate() {
        permisos.find { p ->
            p.access == 'delegate'
        }
    }

    def getWriters() {
        permisos.findAll { p ->
            p.access == 'write'
        }
    }

    def getReaders() {
        permisos.findAll { p ->
            p.access == 'read'
        }
    }

    def isOwner(username) {
        owner.username == username
    }

    def isDelegate(username) {
        executiveDelegate.username == username
    }

    def isWriter(username) {
        writers*.username.contains(username)
    }

    def isReader(username) {
        readers*.username.contains(username)
    }

}
