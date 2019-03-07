package mx.gob.fgjez.gebci.carpetas

import grails.plugin.springsecurity.annotation.Secured
import mx.gob.fgjez.gebci.abac.ResourceAccessController

import static org.springframework.http.HttpStatus.FORBIDDEN

@Secured("isAuthenticated()")
class DomicilioController extends ResourceAccessController<Domicilio> {

    static responseFormats = ['json', 'xml']

    DomicilioController() {
        super(Domicilio)
    }

    @Override
    def index(Integer max) {
        Carpeta carpeta = Carpeta.findById(params.carpetaId)
        if (carpeta == null || permissionService.canRead(carpeta.authKey)) {
            return super.index(max)
        } else {
            render status: FORBIDDEN
        }
    }

    @Override
    protected List<Domicilio> listAllResources(Map params) {
        Domicilio.findAll(params) { carpeta.id == params.carpetaId }
    }

    @Override
    protected Domicilio queryForResource(Serializable id) {
        final carpetaId = params.carpetaId
        Domicilio.find { carpeta.id == carpetaId && id == id }
    }

    @Override
    protected Domicilio createResource() {
        Domicilio instance = super.createResource() as Domicilio
        instance.carpeta = Carpeta.get(params.carpetaId)
        return instance
    }
}
