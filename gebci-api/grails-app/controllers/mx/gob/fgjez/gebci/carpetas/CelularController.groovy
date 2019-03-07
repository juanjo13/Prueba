package mx.gob.fgjez.gebci.carpetas

import grails.plugin.springsecurity.annotation.Secured
import mx.gob.fgjez.gebci.abac.ResourceAccessController

import static org.springframework.http.HttpStatus.FORBIDDEN

@Secured("isAuthenticated()")
class CelularController extends ResourceAccessController<Celular> {

    static responseFormats = ['json', 'xml']

    CelularController() {
        super(Celular)
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
    protected List<Celular> listAllResources(Map params) {
        Celular.findAll(params) { carpeta.id == params.carpetaId }
    }

    @Override
    protected Celular queryForResource(Serializable id) {
        final carpetaId = params.carpetaId
        return Celular.find { carpeta.id == carpetaId && id == id }
    }

    @Override
    protected Celular createResource() {
        Celular instance = super.createResource() as Celular
        instance.carpeta = Carpeta.get(params.carpetaId)
        return instance
    }
}
