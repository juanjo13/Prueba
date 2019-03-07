package mx.gob.fgjez.gebci.carpetas

import grails.plugin.springsecurity.annotation.Secured
import mx.gob.fgjez.gebci.abac.ResourceAccessController

import static org.springframework.http.HttpStatus.FORBIDDEN

@Secured("isAuthenticated()")
class TarjetaBancariaController extends ResourceAccessController<TarjetaBancaria> {

    static responseFormats = ['json', 'xml']

    TarjetaBancariaController() {
        super(TarjetaBancaria)
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
    protected List<TarjetaBancaria> listAllResources(Map params) {
        TarjetaBancaria.findAll(params) { carpeta.id == params.carpetaId }
    }

    @Override
    protected TarjetaBancaria queryForResource(Serializable id) {
        final carpetaId = params.carpetaId
        TarjetaBancaria.find { carpeta.id == carpetaId && id == id }
    }

    @Override
    protected TarjetaBancaria createResource() {
        TarjetaBancaria instance = super.createResource() as TarjetaBancaria
        instance.carpeta = Carpeta.get(params.carpetaId)
        return instance
    }
}