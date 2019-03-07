package mx.gob.fgjez.gebci.abac

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import mx.gob.fgjez.gebci.carpetas.Carpeta

@EqualsAndHashCode(includes = ['authKey', 'username', 'access'])
@ToString(includes = ['authKey', 'username', 'access'], includeNames = true, includePackage = false)
class Permiso {

    enum Level {READ, WRITE, DELEGATE, OWNER}

    Long authKey
    String username
    String access

    static belongsTo = [carpeta: Carpeta]

    void setAccess(String access) {
        this.access = normalizeAccess(access)
    }

    static constraints = {
        authKey nullable: true
        username blank: false
        access blank: false, inList: ['owner','delegate','read','write']
    }

    static mapping = {
        version false
        id generator: 'sequence', params: [sequence: 'et_permiso_id_seq']
        authKey sqlType: 'bigint'
        username length: 50
        access length: 16
    }

    def beforeValidate() {
        access = normalizeAccess(access)
    }

    static String normalizeAccess(String s) {
        if (s.toLowerCase() in ['writer','write','w','+w'])
            return 'write'
        if (s.toLowerCase() in ['reader','read','r','+r'])
            return 'read'
        if (s.toLowerCase() in ['owner','owns','o','+o'])
            return 'owner'
        if (s.toLowerCase() in ['delegate','d','+d'])
            return 'delegate'
        return s
    }
}
