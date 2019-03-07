package mx.gob.fgjez.gebci.carpetas


import mx.gob.fgjez.gebci.geo.Asentamiento
import mx.gob.fgjez.gebci.geo.Ciudad
import mx.gob.fgjez.gebci.geo.Estado
import mx.gob.fgjez.gebci.geo.Municipio

class Region extends Lugar {

    Estado estado
    Municipio municipio
    Ciudad ciudad
    Asentamiento asentamiento

    static constraints = {
        importFrom Lugar

        estado nullable: false
        municipio nullable: true
        ciudad nullable: true
        asentamiento nullable: true
    }

    def getTipoLugar() {
        'region'
    }
}
