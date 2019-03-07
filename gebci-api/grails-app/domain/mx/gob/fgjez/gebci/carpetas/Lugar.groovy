package mx.gob.fgjez.gebci.carpetas

import mx.gob.fgjez.gebci.AccessControlledResource

class Lugar extends AccessControlledResource {

    Carpeta carpeta

    String descripcion
    BigDecimal latitud
    BigDecimal longitud

    String rol
    String estatus

    static constraints = {
        importFrom AccessControlledResource

        latitud scale:6, nullable: true
        longitud scale:6, nullable: true
        descripcion nullable: true
        rol nullable: true
        estatus nullable: true
    }

    static mapping = {
        version false
        tablePerHierarchy false
        id generator: 'sequence', params: [sequence:'et_lugar_id_seq']
        authKey updateable: false
    }

    def getTipoLugar() {
        'lugar'
    }
}
