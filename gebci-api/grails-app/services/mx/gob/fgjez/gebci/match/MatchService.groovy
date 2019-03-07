package mx.gob.fgjez.gebci.match

import grails.gorm.transactions.Transactional
import grails.plugins.rest.client.RestBuilder
import mx.gob.fgjez.gebci.carpetas.Persona

@Transactional
class MatchService {

    def grailsApplication
    def grailsLinkGenerator

    def matchByName(Persona p) {
        [
            subscribe('NOMBRE', p.nombreCompleto),
            subscribe('NOMBRE_PERICIALES', p.nombreCompletoInverso)
        ].collect { job ->
            new MatchJob(jobId: job.id, tipo: 'nombre', entrada: job.entrada, estatus: 'ACTIVO', persona: p, carpeta: p.carpeta, authKey: p.authKey).save()
        }
    }

    def matchByAlias(Persona p) {
        p.alias.collect { a ->
            subscribe('ALIAS', a)
        }.collect { job ->
            new MatchJob(jobId: job.id, tipo: 'alias', entrada: job.entrada, estatus: 'ACTIVO', persona: p, carpeta: p.carpeta, authKey: p.authKey).save()
        }
    }

    private def subscribe(t, e) {
        String url = grailsApplication.config.getProperty('suscripcionCotejos.trabajoEndPoint')
        String callback = grailsLinkGenerator.link(controller: 'match', action: 'notification', absolute: true)
        callback = callback.replace('localhost', InetAddress.getLocalHost().getHostAddress())
        def resp = new RestBuilder().post(url) {
            contentType "application/json"
            json {
                tipo = t
                entrada = e
                urlNotificacion = callback
            }
        }
        new Trabajo(resp.json)
    }

    def hasJob(Persona p, String t) {
        final carpetaId = p.carpeta.id
        final personaId = p.id
        MatchJob.find { carpeta.id == carpetaId && persona.id == personaId && estatus != 'CERRADO' && tipo == t} != null
    }

    def status(Persona p) {
        final carpetaId = p.carpeta.id
        final personaId = p.id
        def jobs = MatchJob.findAll { carpeta.id == carpetaId && persona.id == personaId && estatus != 'CERRADO' }
        if (!jobs) {
            return null
        }
        def status = jobs.collect { it.estatus }.toSet()
        def globalStatus = 'COINCIDENCIAS' in status ? 'COINCIDENCIAS' : 'ACTIVO'
        def porNombre = jobs.findAll { it.tipo == 'nombre' }.collect { [id: (it.id), jobId: (it.jobId), estatus: it.estatus] }
        def porAlias = jobs.findAll { it.tipo == 'alias' }.collect { [id: (it.id), jobId: (it.jobId), estatus: it.estatus] }
        return [status: globalStatus, nombre: porNombre, alias: porAlias]
    }
}
