package mx.gob.fgjez.gebci.search

import grails.validation.Validateable

class SearchCriteria implements Validateable {
    String elemento
    String criterio
    String valor


    @Override
    String toString() {
        return "SearchCriteria{" +
                "elemento='" + elemento + '\'' +
                ", criterio='" + criterio + '\'' +
                ", valor='" + valor + '\'' +
                '}'
    }
}
