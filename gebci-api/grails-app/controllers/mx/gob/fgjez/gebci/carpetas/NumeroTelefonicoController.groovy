package mx.gob.fgjez.gebci.carpetas

import grails.plugin.springsecurity.annotation.Secured
import mx.gob.fgjez.gebci.abac.ResourceAccessController

import static org.springframework.http.HttpStatus.FORBIDDEN

@Secured("isAuthenticated()")
class NumeroTelefonicoController extends ResourceAccessController<NumeroTelefonico> {
    static responseFormats = ['json', 'xml']

    NumeroTelefonicoController() {
        super(NumeroTelefonico)
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
    protected List<NumeroTelefonico> listAllResources(Map params) {
        NumeroTelefonico.findAll(params) { carpeta.id == params.carpetaId }
    }

    @Override
    protected NumeroTelefonico queryForResource(Serializable id) {
        final carpetaId = params.carpetaId
        return NumeroTelefonico.find { carpeta.id == carpetaId && id == id }
    }

    @Override
    protected NumeroTelefonico createResource() {
        def final input = request.JSON
        NumeroTelefonico instance
        if (input.telefono) {
            instance = NumeroTelefonico.parse(input.telefono)
        } else {
            instance = new NumeroTelefonico()
        }
        bindData(instance, input)
        instance.carpeta = Carpeta.get(params.carpetaId)
        return instance
    }
}
