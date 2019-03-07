package mx.gob.fgjez.gebci.catalog

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class DelitoSpec extends Specification implements DomainUnitTest<Delito> {

    def setup() {
    }

    def cleanup() {
    }

    void 'test domain class validation'() {
        when: 'A domain class is saved with invalid data'
            Delito delito = new Delito(clave: '', descripcion: 'desc')
            delito.save()

        then: 'There were errors and the data was not saved'
            delito.hasErrors()
            delito.errors.getFieldError('clave')
            delito.count() == 0
    }

    void 'test domain class validation 2'() {
        when: 'A domain class is saved with invalid data'
        Delito delito = new Delito(clave: 'c', descripcion: '')
        delito.save()

        then: 'There were errors and the data was not saved'
        delito.hasErrors()
        delito.errors.getFieldError('descripcion')
        delito.count() == 0
    }

    void 'test domain class validation 3'() {
        when: 'A domain class is saved with invalid data'
        Delito delito = new Delito(clave: null, descripcion: 'desc')
        delito.save()

        then: 'There were errors and the data was not saved'
        delito.hasErrors()
        delito.errors.getFieldError('clave')
        delito.count() == 0
    }


    void 'test domain class validation 4'() {
        when: 'A domain class is saved with invalid data'
        Delito delito = new Delito(clave: 'c', descripcion: null)
        delito.save()

        then: 'There were errors and the data was not saved'
        delito.hasErrors()
        delito.errors.getFieldError('descripcion')
        delito.count() == 0
    }
}
