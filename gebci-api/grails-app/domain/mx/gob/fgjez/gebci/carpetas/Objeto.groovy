package mx.gob.fgjez.gebci.carpetas

import mx.gob.fgjez.gebci.AccessControlledResource

abstract class Objeto extends AccessControlledResource {

    Carpeta carpeta

    String rol
    String estatus

    static constraints = {
        importFrom AccessControlledResource

        rol nullable: true
        estatus nullable: true
    }

    static mapping = {
        version false
        tablePerHierarchy false
        id generator: 'sequence', params: [sequence: 'et_objeto_id_seq']
        authKey updateable: false
        rol length: 30
        estatus length: 30
    }

    protected abstract String getTipoObjeto();
}
