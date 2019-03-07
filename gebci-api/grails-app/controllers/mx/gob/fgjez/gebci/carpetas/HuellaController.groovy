package mx.gob.fgjez.gebci.carpetas

import grails.plugin.springsecurity.annotation.Secured
import mx.gob.fgjez.gebci.abac.ResourceAccessController

import static org.springframework.http.HttpStatus.FORBIDDEN

@Secured("isAuthenticated()")
//@Secured(['ROLE_SECRETARIO'])
class HuellaController extends ResourceAccessController<Huella> {

    static responseFormats = ['json', 'xml']

    HuellaController() {		
        super(Huella)
		log.debug("constructor at huellacontroller");
    }

    @Override
    def index(Integer max) {log.debug("index at huellacontroller");
        Carpeta carpeta = Carpeta.findById(params.carpetaId)
        if (carpeta == null || permissionService.canRead(carpeta.authKey)) {
            return super.index(max)
        } else {
            //render status: FORBIDDEN
			return super.index(max)
        }
    }

    @Override
    protected List<Huella> listAllResources(Map params) {
        Huella.findAll(params) { carpeta.id == params.carpetaId }
    }

    @Override
    protected Huella queryForResource(Serializable id) {
        final carpetaId = params.carpetaId
        Huella.find { carpeta.id == carpetaId && id == id }
    }

    @Override
    protected Huella createResource() {
        Huella instance = super.createResource() as Huella
        instance.carpeta = Carpeta.get(params.carpetaId)
        return instance
    }
}