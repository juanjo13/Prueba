package mx.gob.fgjez.gebci.search

import grails.plugin.springsecurity.annotation.Secured
import groovy.json.JsonBuilder
import mx.gob.fgjez.gebci.carpetas.*

@Secured("isAuthenticated()")
class SearchController {

    def searchService

    static responseFormats = ['json', 'xml']

    def search(SearchCriteria criteria) {
        def results = searchService.search(criteria, params.max, params.offset)
        respond resultList: results.list
    }

    def searchElements() {
        JsonBuilder builder = new JsonBuilder()
        def elementos = [Arma, NumeroTelefonico, Celular, Domicilio, Region, Lugar, Persona, TarjetaBancaria, Vehiculo]
                .collect { e -> grailsApplication.getDomainClass(e.getName()) }
                .collect { e -> [
                    elemento: e.clazz.getSimpleName(),
                    criterios: e.getProperties()
                        .findAll { it.type == String.class }
                        .findAll { !(it.name in ['rol','estatus','tipoObjeto']) }
                        .collect { it.name }
                ]}
        builder(elementos)
        render text: builder.toPrettyString(), contentType: 'application/json'
    }
}
