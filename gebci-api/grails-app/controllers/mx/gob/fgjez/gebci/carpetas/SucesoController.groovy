package mx.gob.fgjez.gebci.carpetas

import grails.plugin.springsecurity.annotation.Secured
import mx.gob.fgjez.gebci.abac.ResourceAccessController

import static org.springframework.http.HttpStatus.FORBIDDEN

@Secured("isAuthenticated()")
class SucesoController extends ResourceAccessController<Suceso> {

    static responseFormats = ['json', 'xml']

    SucesoController() {
        super(Suceso)
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
    protected List<Suceso> listAllResources(Map params) {
        Suceso.findAll(params) { carpeta.id == params.carpetaId }
    }

    @Override
    protected Suceso queryForResource(Serializable id) {
        final carpetaId = params.carpetaId
        Suceso.find { carpeta.id == carpetaId && id == id }
    }

    @Override
    protected Suceso createResource() {
        Suceso instance = super.createResource() as Suceso
        instance.carpeta = Carpeta.get(params.carpetaId)
        return instance
    }
}
