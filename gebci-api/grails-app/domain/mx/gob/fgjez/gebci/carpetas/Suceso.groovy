package mx.gob.fgjez.gebci.carpetas

import grails.databinding.BindingFormat
import mx.gob.fgjez.gebci.AccessControlledResource

class Suceso extends AccessControlledResource {

    String descripcion

    @BindingFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    Date puntoEnTiempo
    @BindingFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    Date inicio
    @BindingFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    Date fin

    Integer duracion

    Lugar lugar
    String rol
    String estatus

    Carpeta carpeta

    Set<Persona> personas
    Set<Objeto> objetos


    static constraints = {
        authKey nullable: true
        rol nullable: true
        estatus nullable: true
        puntoEnTiempo nullable: true
        inicio nullable: true
        fin nullable: true
        duracion nullable: true
        descripcion nullable: true
        lugar nullable: true
    }

    static mapping = {
        version false
        id generator: 'sequence', params: [sequence:'et_suceso_id_seq']
        authKey updateable: false
        descripcion sqlType: 'text'
        personas joinTable: [name: "suceso_persona_relacionada", key: "suceso", column: "persona"]
        objetos joinTable: [name: "suceso_objeto_relacionado", key: "suceso", column: "objeto"]
    }

    def categorize() {
        println 'inside categorize'
        def categorization =
                objetos.collect{ it.tipoObjeto }.unique()
                .collect { it + 's' }
                .collectEntries{ [(it): []] }
        objetos.each { categorization[it.tipoObjeto + 's'] << it.id }
        categorization
    }
}
