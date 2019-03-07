package mx.gob.fgjez.gebci.carpetas

import grails.gorm.services.Service

@Service(Celular)
interface CelularService {

    Celular get(Serializable id)

    List<Celular> list(Map args)

    Long count()

    void delete(Serializable id)

    Celular save(Celular celular)

}