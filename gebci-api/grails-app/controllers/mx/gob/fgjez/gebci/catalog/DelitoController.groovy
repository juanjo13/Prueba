package mx.gob.fgjez.gebci.catalog

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

class DelitoController extends RestfulController {

    static responseFormats = ['json', 'xml']

    DelitoController() {
        super(Delito)
    }

    @Secured('ROLE_USER')
    def index() {
        log.debug("inside index at delitocontroller")
        super.index()
    }

    @Secured('ROLE_USER')
    def show() {
        log.debug("inside show")
        super.show()
    }

    @Secured('ROLE_ADMIN')
    def save() {
        log.debug("inside save")
        super.save()
    }

    @Secured('ROLE_ADMIN')
    def update() {
        log.debug("inside update")
        super.update()
    }

    @Secured('ROLE_ADMIN')
    def delete() {
        log.debug("inside delete")
        super.delete()
    }
}
