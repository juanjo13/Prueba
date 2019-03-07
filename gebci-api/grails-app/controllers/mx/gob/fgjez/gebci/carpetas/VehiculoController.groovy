package mx.gob.fgjez.gebci.carpetas

import grails.plugin.springsecurity.annotation.Secured
import mx.gob.fgjez.gebci.abac.ResourceAccessController

import static org.springframework.http.HttpStatus.FORBIDDEN

@Secured("isAuthenticated()")
class VehiculoController extends ResourceAccessController<Vehiculo> {

    static responseFormats = ['json', 'xml']

    VehiculoController() {
        super(Vehiculo)
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
    protected List<Vehiculo> listAllResources(Map params) {
        Vehiculo.findAll(params) { carpeta.id == params.carpetaId }
    }

    @Override
    protected Vehiculo queryForResource(Serializable id) {
        final carpetaId = params.carpetaId
        Vehiculo.find { carpeta.id == carpetaId && id == id }
    }

    @Override
    protected Vehiculo createResource() {
        Vehiculo instance = super.createResource() as Vehiculo
        instance.carpeta = Carpeta.get(params.carpetaId)
        return instance
    }
}