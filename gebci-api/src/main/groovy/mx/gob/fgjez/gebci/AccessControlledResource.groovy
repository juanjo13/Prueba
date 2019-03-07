package mx.gob.fgjez.gebci

import groovy.transform.ToString

@ToString(includes=['authKey'], includeNames=true, includePackage=false)
abstract class AccessControlledResource {

    Long authKey

    static constraints = {
        authKey nullable: true
    }
}
