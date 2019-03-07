package mx.gob.fgjez.gebci.carpetas

import grails.plugin.springsecurity.annotation.Secured
import mx.gob.fgjez.gebci.abac.ResourceAccessController

import static org.springframework.http.HttpStatus.FORBIDDEN

@Secured("isAuthenticated()")
class ArmaController extends ResourceAccessController<Arma> {

    static responseFormats = ['json', 'xml']

    ArmaController() {
        super(Arma);
		log.debug("building armacontroller()");
    }

    @Override
    def index(Integer max) {log.debug("index at armacontroller");
        Carpeta carpeta = Carpeta.findById(params.carpetaId)
        if (carpeta == null || permissionService.canRead(carpeta.authKey)) {
            return super.index(max)
        } else {
            render status: FORBIDDEN
        }
    }

    @Override
    protected List<Arma> listAllResources(Map params) {
        Arma.findAll(params) { carpeta.id == params.carpetaId }
    }

    @Override
    protected Arma queryForResource(Serializable id) {
        final carpetaId = params.carpetaId
        Arma.find { carpeta.id == carpetaId && id == id }
    }

    @Override
    protected Arma createResource() {
        Arma instance = super.createResource() as Arma
        instance.carpeta = Carpeta.get(params.carpetaId)
        return instance
    }
}