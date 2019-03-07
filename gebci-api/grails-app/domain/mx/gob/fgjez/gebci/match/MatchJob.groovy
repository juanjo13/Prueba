package mx.gob.fgjez.gebci.match

import mx.gob.fgjez.gebci.AccessControlledResource
import mx.gob.fgjez.gebci.carpetas.Carpeta
import mx.gob.fgjez.gebci.carpetas.Persona

class MatchJob extends AccessControlledResource {

    Long jobId
    String tipo
    String entrada
    String estatus

    Carpeta carpeta
    Persona persona




    static hasMany = [
            actions: MatchJobAction,
            notifications: MatchJobNotification
    ]

    static constraints = {
        importFrom AccessControlledResource
    }

    static mapping = {
        version false
        id generator: 'sequence', params: [sequence: 'match_job_id_seq']
        notifications cascade: 'all'
    }
}
