package mx.gob.fgjez.gebci.carpetas

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class NumeroTelefonicoSpec extends Specification implements DomainUnitTest<NumeroTelefonico> {

    def setup() {
    }

    def cleanup() {
    }

    void "default country code"() {
        def tel = new NumeroTelefonico()
        expect:"default country code is 52"
            tel.codigoPais == '52'
    }

    void "lada 33 is valid"() {
        def tel = new NumeroTelefonico(codigoArea: '33', numero: '12345678')
        expect: "valid lada"
        tel.codigoArea == '33' && tel.validate()
    }

    void "lada 55 is valid"() {
        def tel = new NumeroTelefonico(codigoArea:'55', numero:'12345678')
        expect:"valid lada"
            tel.codigoArea == '55' && tel.validate()
    }

    void "lada 81 is valid"() {
        def tel = new NumeroTelefonico(codigoArea:'81', numero:'12345678')
        expect:"valid lada"
            tel.codigoArea == '81' && tel.validate()
    }

    void "1 digit lada is not valid"() {
        def tel = new NumeroTelefonico(codigoArea:'1', numero:'123456789')
        expect:"invalid lada"
            !tel.validate()
    }

    void "2 digit lada not in (33,55,81) is not valid"() {
        def tel = new NumeroTelefonico(codigoArea:'99', numero:'12345678')
        expect:"invalid lada"
            !tel.validate()
    }

    void "3 digit lada is valid"() {
        def tel = new NumeroTelefonico(codigoArea:'999', numero:'1234567')
        expect:"valid lada"
            tel.codigoArea == '999' && tel.validate()
    }

    void "2 digit area + number should be 10 digit long success"() {
        def tel = new NumeroTelefonico(codigoArea: '33', numero: '12345678')
        expect: "valid lada + number"
            tel.validate()
    }

    void "3 digit area + number should be 10 digit long success"() {
        def tel = new NumeroTelefonico(codigoArea:'664', numero:'1234567')
        expect:"valid lada + number"
            tel.validate()
    }

    void "2 digita area + number should be 10 digit long; to short failure"() {
        def tel = new NumeroTelefonico(codigoArea:'33', numero:'1234567')
        expect:"invalid lada + number"
            !tel.validate()
    }

    void "2 digita area + number should be 10 digit long; to long failure"() {
        def tel = new NumeroTelefonico(codigoArea: '33', numero: '123456789')
        expect: "invalid lada + number"
            !tel.validate()
    }

    void "3 digita area + number should be 10 digit long; to short failure"() {
        def tel = new NumeroTelefonico(codigoArea: '664', numero: '123456')
        expect: "invalid lada + number"
            !tel.validate()
    }

    void "3 digita area + number should be 10 digit long; to long failure"() {
        def tel = new NumeroTelefonico(codigoArea:'664', numero:'12345678')
        expect:"invalid lada + number"
            !tel.validate()
    }

    void "parse international number success"() {
        def tel = NumeroTelefonico.parse('+44(01865)123456')
        expect: "valid phone number"
            tel.validate()
        and: "matching country code"
            tel.codigoPais == '44'
        and: "matching area code"
            tel.codigoArea == '01865'
        and: "matching number"
            tel.numero == '123456'
    }

    void "parse national number success"() {
        def tel = NumeroTelefonico.parse('(55)12345678')
        expect: "valid phone number"
            tel.validate()
        and: "matching country code"
            tel.codigoPais == '52'
        and: "matching area code"
            tel.codigoArea == '55'
        and: "matching number"
            tel.numero == '12345678'
    }

    void "parse national number success alternate format"() {
        def tel = NumeroTelefonico.parse('5512345678')
        expect: "valid phone number"
            tel.validate()
        and: "matching country code"
            tel.codigoPais == '52'
        and: "matching area code"
            tel.codigoArea == '55'
        and: "matching number"
            tel.numero == '12345678'
    }

    void "parse failure"() {
        when:
            NumeroTelefonico.parse('x1234567890')
        then: "parse exception"
            thrown IllegalArgumentException

        when:
            NumeroTelefonico.parse('44(01865)123456')
        then: "parse exception"
            thrown IllegalArgumentException

        when:
            NumeroTelefonico.parse('(55)1234567')
        then: "parse exception"
            thrown IllegalArgumentException

        when:
            NumeroTelefonico.parse('(55)123456789')
        then: "parse exception"
            thrown IllegalArgumentException

        when:
            NumeroTelefonico.parse('551234567')
        then: "parse exception"
            thrown IllegalArgumentException

        when:
            NumeroTelefonico.parse('55123456789')
        then: "parse exception"
            thrown IllegalArgumentException

        when:
            NumeroTelefonico.parse('(664)123456')
        then: "parse exception"
            thrown IllegalArgumentException

        when:
            NumeroTelefonico.parse('(664)12345678')
        then: "parse exception"
            thrown IllegalArgumentException

        when:
            NumeroTelefonico.parse('664123456')
        then: "parse exception"
            thrown IllegalArgumentException

        when:
            NumeroTelefonico.parse('66412345678')
        then: "parse exception"
            thrown IllegalArgumentException
    }

    void "formatted number"() {
        def tel
        when:
            tel = NumeroTelefonico.parse('5512345678').format()
        then: "formatted phone number should look like this"
            tel == '(55) 1234-5678'

        when:
            tel = NumeroTelefonico.parse('6641234567').format()
        then: "formatted phone number should look like this"
            tel == '(664) 123-4567'

        when:
            tel = NumeroTelefonico.parse('+44(020)12345678').format()
        then: "formatted phone number should look like this"
            tel == '+442012345678'
    }

}
