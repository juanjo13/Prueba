package mx.gob.fgjez.gebci.carpetas

import grails.plugin.springsecurity.annotation.Secured
import mx.gob.fgjez.gebci.abac.ResourceAccessController

import static org.springframework.http.HttpStatus.FORBIDDEN

@Secured("isAuthenticated()")
class RostroController extends ResourceAccessController<Rostro> {

    static responseFormats = ['json', 'xml']

    RostroController() {		
        super(Rostro)
		log.debug("constructor at rostrocontroller");
    }

    @Override
    def index(Integer max) {log.debug("index at rostrocontroller");
        Carpeta carpeta = Carpeta.findById(params.carpetaId)
        if (carpeta == null || permissionService.canRead(carpeta.authKey)) {
            return super.index(max)
        } else {
            //render status: FORBIDDEN
			return super.index(max)
        }
    }

    @Override
    protected List<Rostro> listAllResources(Map params) {
        Rostro.findAll(params) { carpeta.id == params.carpetaId }
    }

    @Override
    protected Rostro queryForResource(Serializable id) {
        final carpetaId = params.carpetaId
        Rostro.find { carpeta.id == carpetaId && id == id }
    }

    @Override
    protected Rostro createResource() {
        Rostro instance = super.createResource() as Rostro
        instance.carpeta = Carpeta.get(params.carpetaId)
        return instance
    }
}