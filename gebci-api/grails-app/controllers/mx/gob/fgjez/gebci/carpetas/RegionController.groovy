package mx.gob.fgjez.gebci.carpetas

import grails.plugin.springsecurity.annotation.Secured
import mx.gob.fgjez.gebci.abac.ResourceAccessController

import static org.springframework.http.HttpStatus.FORBIDDEN

@Secured("isAuthenticated()")
class RegionController extends ResourceAccessController<Region> {

    static responseFormats = ['json', 'xml']

    RegionController() {
        super(Region)
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
    protected List<Region> listAllResources(Map params) {
        Region.findAll(params) { carpeta.id == params.carpetaId }
    }

    @Override
    protected Region queryForResource(Serializable id) {
        final carpetaId = params.carpetaId
        Region.find { carpeta.id == carpetaId && id == id }
    }

    @Override
    protected Region createResource() {
        Region instance = super.createResource() as Region
        instance.carpeta = Carpeta.get(params.carpetaId)
        return instance
    }
}
