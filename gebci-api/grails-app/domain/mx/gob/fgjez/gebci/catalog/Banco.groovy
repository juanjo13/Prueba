package mx.gob.fgjez.gebci.catalog

import java.sql.Timestamp

class Banco implements Serializable {

    Long eid
    Timestamp recActiveFrom
    boolean active
    String descripcion

    static constraints = {
        descripcion blank: false
        recActiveFrom nullable: true
    }

    static mapping = {
        version false
        id composite: ['eid', 'recActiveFrom']
        eid generator: 'sequence', params: [sequence: 'xr_banco_id_seq']
    }

}
