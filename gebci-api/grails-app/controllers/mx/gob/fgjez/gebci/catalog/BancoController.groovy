package mx.gob.fgjez.gebci.catalog

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

class BancoController extends RestfulController {

    static responseFormats = ['json', 'xml']

    BancoController() {
        super(Banco)
    }

    @Secured('ROLE_USER')
    def index() {
        super.index()
    }

    @Secured('ROLE_USER')
    def show() {
        super.show()
    }

    @Secured('ROLE_ADMIN')
    def save() {
        super.save()
    }

    @Secured('ROLE_ADMIN')
    def update() {
        super.update()
    }

    @Secured('ROLE_ADMIN')
    def delete() {
        super.delete()
    }

    @Override
    protected List<Banco> listAllResources(Map params) {
        Banco.findAll(params) { active == true }
    }

    protected Banco queryForResource(Serializable id) {
        Banco.find(){ eid == id && active }
    }

}
