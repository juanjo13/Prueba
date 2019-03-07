package mx.gob.fgjez.gebci.match

class MatchJobNotification {
    Date notifiedOn

    static constraints = {}

    static belongsTo = [
            matchJob: MatchJob
    ]
    static mapping = {
        version false
        id generator: 'sequence', params: [sequence: 'match_job_notification_id_seq']
        matchJob column: 'match_job', cascade: 'all'
    }
}
