package mx.gob.fgjez.gebci.carpetas

import grails.plugin.springsecurity.annotation.Secured
import mx.gob.fgjez.gebci.abac.ResourceAccessController

import static org.springframework.http.HttpStatus.FORBIDDEN

@Secured("isAuthenticated()")
class LlamadaTelefonicaController extends ResourceAccessController<LlamadaTelefonica> {

    static responseFormats = ['json', 'xml']

    LlamadaTelefonicaController() {
        super(LlamadaTelefonica)
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
    protected List<LlamadaTelefonica> listAllResources(Map params) {
        LlamadaTelefonica.findAll(params) { carpeta.id == params.carpetaId }
    }

    @Override
    protected LlamadaTelefonica queryForResource(Serializable id) {
        final carpetaId = params.carpetaId
        LlamadaTelefonica.find { carpeta.id == carpetaId && id == id }
    }

    @Override
    protected LlamadaTelefonica createResource() {
        LlamadaTelefonica instance = super.createResource() as LlamadaTelefonica
        instance.carpeta = Carpeta.get(params.carpetaId)
        return instance
    }
}
