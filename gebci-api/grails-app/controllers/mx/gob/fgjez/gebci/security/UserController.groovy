package mx.gob.fgjez.gebci.security

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

@Secured("isAuthenticated()")
class UserController extends RestfulController<User> {

    static responseFormats = ['json', 'xml']

    UserController() {
        super(User, true)
    }

    def index() {log.debug("index at usercontroller");
        respond UserRole.findAll(params) {
            role.authority in ['ROLE_MINISTERIO_PUBLICO', 'ROLE_SECRETARIO', 'ROLE_POLICIA_MINISTERIAL']
        }*.user
    }

    def ministeriosPublicos() {
        respond UserRole.findAll(params) {
            role.authority == 'ROLE_MINISTERIO_PUBLICO'
        }*.user
    }

}
