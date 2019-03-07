package mx.gob.fgjez.gebci.match

class MatchJobAction {
    String item
    String action

    static constraints = {}

    static belongsTo = [
            matchJob: MatchJob
    ]
    static mapping = {
        version false
        id generator: 'sequence', params: [sequence: 'match_job_action_id_seq']
        matchJob column: 'match_job'
    }
}
