package mx.gob.fgjez.gebci.carpetas

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.web.http.HttpHeaders
import mx.gob.fgjez.gebci.abac.ResourceAccessController
import mx.gob.fgjez.gebci.catalog.Delito
import mx.gob.fgjez.gebci.geo.Procedencia
import org.springframework.dao.DuplicateKeyException

import static org.springframework.http.HttpStatus.FORBIDDEN
import static org.springframework.http.HttpStatus.OK

@Secured("isAuthenticated()")
class CarpetaController extends ResourceAccessController<Carpeta> {
    static responseFormats = ['json', 'xml']

    CarpetaController() {
        super(Carpeta)
    }

    @Override
    Object save() {
        try {
            return super.save()
        } catch(DuplicateKeyException e) {
            render status: 409, message: 'número de carpeta ya existe en sistema'
            return null
        }
    }

    @Override
    //@Secured(['ROLE_MINISTERIO_PUBLICO', 'ROLE_SECRETARIO'])
    def create() {
        super.create()
    }

    protected Carpeta createResource() {
        Carpeta instance = new Carpeta()
        bindData instance, getObjectToBind()
        instance.permisos = permissionService.createPermissions(instance, instance.permisos)
        instance
    }

    @Transactional
    def update() {
        Carpeta instance = queryForResource(params.id)
        if (instance != null && !permissionService.canWrite(instance.authKey)) {
            render status: FORBIDDEN
            return
        }

        if(handleReadOnly()) {
            return
        }

        if (instance == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        def oldPerms = instance.permisos
        instance.properties = getObjectToBind()
        instance.delito = Delito.read(instance.delito.id)
        instance.procedencia = Procedencia.read(instance.procedencia.id)
        def newPerms = instance.permisos
        if (instance.permisos != null) {
            instance.permisos = permissionService.updatePermissions(oldPerms, newPerms)
        }

        if (instance.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond instance.errors, view:'edit' // STATUS CODE 422
            return
        }

        updateResource instance
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [classMessageArg, instance.id])
                redirect instance
            }
            '*'{
                response.addHeader(HttpHeaders.LOCATION,
                        grailsLinkGenerator.link( resource: this.controllerName, action: 'show',id: instance.id, absolute: true,
                                namespace: hasProperty('namespace') ? this.namespace : null ))
                respond instance, [status: OK]
            }
        }
    }
}
