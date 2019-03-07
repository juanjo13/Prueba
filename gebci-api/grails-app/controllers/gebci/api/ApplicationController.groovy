package gebci.api

import grails.core.GrailsApplication
import grails.plugins.*

import static org.springframework.http.HttpStatus.NO_CONTENT

class ApplicationController implements PluginManagerAware {

    GrailsApplication grailsApplication
    GrailsPluginManager pluginManager

    def index() {
        render status: NO_CONTENT
    }

}
