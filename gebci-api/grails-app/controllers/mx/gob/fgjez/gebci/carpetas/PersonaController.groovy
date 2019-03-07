package mx.gob.fgjez.gebci.carpetas

import grails.plugin.springsecurity.annotation.Secured
import mx.gob.fgjez.gebci.abac.ResourceAccessController
import mx.gob.fgjez.gebci.match.MatchJob

import static org.springframework.http.HttpStatus.*

@Secured("isAuthenticated()")
class PersonaController extends ResourceAccessController<Persona> {

    def matchService

    static responseFormats = ['json', 'xml']

    PersonaController() {
        super(Persona)
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
    protected List<Persona> listAllResources(Map params) {
        Persona.findAll(params) { carpeta.id == params.carpetaId }
    }

    @Override
    protected Persona queryForResource(Serializable id) {
        final carpetaId = params.carpetaId
        return Persona.find { carpeta.id == carpetaId && id == id }
    }

    @Override
    protected Persona createResource() {
        Persona instance = super.createResource() as Persona
        instance.carpeta = Carpeta.findById(params.carpetaId)
        return instance
    }

    def lookupSubscribe() {
        final carpetaId = params.carpetaId
        final personaId = params.personaId
        final type = request.JSON.type

        println("Carpeta Id: " + carpetaId + " Persona Id: "  + personaId)

        Carpeta carpeta = Carpeta.findById(carpetaId)
        if (permissionService.canWrite(carpeta.authKey)) {
            Persona persona = Persona.find { carpeta.id == carpetaId && id == personaId }
            if (!persona) {
                render status: NOT_FOUND
                return
            }
            if (type == 'nombre' && !matchService.hasJob(persona, 'nombre')) {
                matchService.matchByName(persona)
            } else if (type == 'alias' && !matchService.hasJob(persona, 'alias')) {
                matchService.matchByAlias(persona)
            } else {
                render status: UNPROCESSABLE_ENTITY
                return
            }
            render status: CREATED
        } else {
            render status: FORBIDDEN
        }
    }

    def lookupStatus() {
        final carpetaId = params.carpetaId
        final personaId = params.personaId
        Persona persona = Persona.find { carpeta.id == carpetaId && id == personaId }
        if (!persona) {
            render status: NOT_FOUND
        }
        def status = matchService.status(persona)
        if (!status) {
            render status: NOT_FOUND, message: 'no hay búsquedas activas'
        } else {
            respond status
        }
    }
}
