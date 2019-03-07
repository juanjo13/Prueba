package mx.gob.fgjez.gebci.search

import grails.gorm.transactions.Transactional

@Transactional
class SearchService {

    def grailsApplication
    def permissionService

    def search(SearchCriteria criteria, max, offset) {
        log.debug(criteria.toString())
        return doSearch(criteria, max, offset)
    }

    private def doSearch(criteria, max, offset) {
        def domain = getElementClass(criteria.elemento)
        validateCriteria(domain, criteria.criterio)
        def matches = fetchMatches(domain, criteria, max, offset)
        def results = matches.collect(this.&toSearchResult)
        ['list':results, 'totalCount':matches.totalCount]
    }

    private def getElementClass(element) {
        def elementClass = "mx.gob.fgjez.gebci.carpetas.${element.capitalize()}"
        grailsApplication.getDomainClass(elementClass)
    }

    private def validateCriteria(domain, criteria) {
        if (!domain.hasProperty(criteria)) {
            throw new IllegalArgumentException()
        }
        if (domain.getPropertyByName(criteria).type != String.class) {
            throw new IllegalArgumentException()
        }
    }

    private def fetchMatches(domain, criteria, max, offset) {
        domain.clazz.createCriteria().list(max: max, offset: offset) {
            ilike (criteria.criterio, "%${criteria.valor}%")
        }
    }

    private def toSearchResult(e) {
        if (permissionService.canRead(e.carpeta.authKey)) {
            return new SearchResult(carpeta: e.carpeta, coincidencia: e)
        } else {
            return new SearchResult(carpeta: e.carpeta, coincidencia: 'CONTENIDO RESTRINGIDO')
        }
    }

}
