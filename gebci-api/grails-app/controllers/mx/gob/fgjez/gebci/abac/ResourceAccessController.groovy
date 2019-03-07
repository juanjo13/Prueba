package mx.gob.fgjez.gebci.abac

import grails.rest.RestfulController
import mx.gob.fgjez.gebci.AccessControlledResource
import org.hibernate.jdbc.Work

import java.sql.Connection
import java.sql.SQLException

import static org.springframework.http.HttpStatus.FORBIDDEN

class ResourceAccessController<T extends AccessControlledResource> extends RestfulController<T> {

    def springSecurityService
    def permissionService
    def sessionFactory

    ResourceAccessController(Class<T> domainClass) {
        this(domainClass, false)
    }

    ResourceAccessController(Class<T> domainClass, boolean readOnly) {
        super(domainClass, readOnly)
    }

    @Override
    def show() {log.debug("show at ResourceAccessController");
        T resource = queryForResource(params.id)
        if (resource && !permissionService.canRead(resource.authKey))
            render status: FORBIDDEN
        respond resource
    }

    @Override
    protected T saveResource(T resource) {
        setTransactionLocalVariables()
        return super.saveResource(resource) as T
    }

    @Override
    def update() {
        T resource = queryForResource(params.id)
        if (resource == null || permissionService.canWrite(resource.authKey)) {
            return super.update()
        } else {
            render status: FORBIDDEN
        }
    }

    @Override
    Object delete() {
        T resource = queryForResource(params.id)
        if (resource == null || permissionService.canWrite(resource.authKey)) {
            return super.delete()
        } else {
            render status: FORBIDDEN
        }
    }

    @Override
    protected void deleteResource(T resource) {
        setTransactionLocalVariables()
        super.deleteResource(resource)
    }

    private setTransactionLocalVariables() {
        sessionFactory.getCurrentSession().doWork(new Work() {
            @Override
            void execute(Connection connection) throws SQLException {
                connection.createStatement().execute "set local gebci.username = '${user()}'"
            }
        })
    }

    private String user() {
        springSecurityService.principal?.username
    }

}
