package mx.gob.fgjez.gebci.match

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Transactional
class MatchController  {
    static responseFormats = ['json', 'xml']

    def notification() {
        final id = request.JSON.trabajoId
        println("Id de trabajo encontrado: " + id)
        MatchJob match = MatchJob.find{ jobId == id }
        println("Match: " + match)
        match.estatus = 'COINCIDENCIAS'
        println("Estatus: " + match.estatus)
        MatchJobNotification matchNotify = new MatchJobNotification()
        matchNotify.notifiedOn = new Date()
        println("Fecha Notificación: " + matchNotify.notifiedOn)
        matchNotify.matchJob = match
        match.notifications.add(matchNotify)
        match.save(flush: true, insert: true)


    }

}
