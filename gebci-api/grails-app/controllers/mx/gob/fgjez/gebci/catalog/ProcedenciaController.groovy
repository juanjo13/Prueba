package mx.gob.fgjez.gebci.catalog

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import mx.gob.fgjez.gebci.geo.Procedencia

@Secured("isAuthenticated()")
class ProcedenciaController extends RestfulController {

    static responseFormats = ['json', 'xml']

    ProcedenciaController() {
        super(Procedencia, true)
    }

    @Override
    protected List<Procedencia> listAllResources(Map params) {
        return Procedencia.findAll(params) {
            estado == '32'
        }
    }

}
