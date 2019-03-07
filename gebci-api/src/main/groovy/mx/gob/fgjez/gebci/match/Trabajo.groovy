package mx.gob.fgjez.gebci.match

import grails.validation.Validateable

class Trabajo implements Serializable, Validateable {

    long id
    String tipo
    String entrada
    String estatus
    String urlNotificacion
    String estatusNotificacion
    String creado
    String ejecutado
    def trabajosEncontrados =[]

    static constraints = {}

}
