package mx.gob.fgjez.gebci.security

class UserDetails {

    String nombre
    String paterno
    String materno
    String email

    static belongsTo = [user: User]

    static constraints = {
        nombre blank: false, size: 2..50
        paterno blank: false, size: 2..50
        materno nullable: true, maxSize: 50
        email blank: false, email: true
    }

    static mapping = {
        table schema: 'sec'
        id generator: 'sequence', params: [sequence: 'user_details_id_seq']
    }
}
