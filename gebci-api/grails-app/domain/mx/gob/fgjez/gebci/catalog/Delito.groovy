package mx.gob.fgjez.gebci.catalog

class Delito {

    String descripcion

    static constraints = {
        descripcion blank: false
    }

    static mapping = {
        version false
        id generator: 'sequence', params: [sequence: 'xr_delito_id_seq']
    }
}
