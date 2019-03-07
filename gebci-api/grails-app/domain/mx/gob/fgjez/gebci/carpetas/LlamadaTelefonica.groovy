package mx.gob.fgjez.gebci.carpetas

import grails.databinding.BindingFormat
import mx.gob.fgjez.gebci.AccessControlledResource

class LlamadaTelefonica extends AccessControlledResource {

    NumeroTelefonico origen
    NumeroTelefonico destino

    @BindingFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    Date puntoEnTiempo
    @BindingFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    Date inicio
    @BindingFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    Date fin
    Integer duracion

    String descripcion

    String rol
    String estatus

    Carpeta carpeta

    static constraints = {
        authKey nullable: true
        rol nullable: true
        estatus nullable: true
        puntoEnTiempo nullable: true
        inicio nullable: true
        fin nullable: true
        duracion nullable: true
        descripcion nullable: true
    }

    static mapping = {
        version false
        id generator: 'sequence', params: [sequence:'et_llamada_telefonica_id_seq']
        authKey updateable: false
        origen column: 'origen'
        destino column: 'destino'
        descripcion sqlType: 'text'
    }
}
