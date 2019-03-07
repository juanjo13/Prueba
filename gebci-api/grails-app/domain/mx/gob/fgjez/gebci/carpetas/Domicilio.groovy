package mx.gob.fgjez.gebci.carpetas


import mx.gob.fgjez.gebci.geo.Asentamiento
import mx.gob.fgjez.gebci.geo.Ciudad
import mx.gob.fgjez.gebci.geo.Estado
import mx.gob.fgjez.gebci.geo.Municipio

class Domicilio extends Lugar {

    Estado estado
    Municipio municipio
    Ciudad ciudad
    Asentamiento asentamiento

    String calle
    String numeroExterior
    String numeroInterior

    static constraints = {
        importFrom Lugar

        ciudad nullable: true
        calle validator:{ it ==~ /(?i)^[-.0-9A-ZÁÉÍÓÚÜÑ ]+\u0024/ }
        numeroExterior validator:{ it ==~ /(?i)^[-.#0-9A-Z ]+$/ }
        numeroInterior nullable: true, validator:{ it == null || it ==~ /(?i)^[-.#0-9A-Z ]+$/ }
    }

    static mapping = {
        numeroExterior column: 'num_ext'
        numeroInterior column: 'num_int'
    }

    def getTipoLugar() {
        'domicilio'
    }

}
