package mx.gob.fgjez.gebci.carpetas

import grails.databinding.BindUsing
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import mx.gob.fgjez.gebci.catalog.Banco

@EqualsAndHashCode(includes = ['id', 'numero', 'banco'])
@ToString(includes = ['id', 'numero', 'banco'], includeNames = true, includePackage = false)
class TarjetaBancaria extends Objeto {

    String numero

    @BindUsing({ obj, source ->
        def final bancoId = source['banco']
        if (bancoId instanceof Integer || bancoId instanceof Long) {
            return Banco.find(){ eid == bancoId && active == true }
        } else if (bancoId instanceof Map && bancoId.id instanceof Integer || bancoId.id instanceof Long) {
            return Banco.find(){ eid == bancoId.id && active == true }
        } else {
            throw new IllegalArgumentException()
        }
    })
    Banco banco

    static constraints = {
        importFrom Objeto

        numero validator: { cn -> cn ==~ /\d+/ }
        banco nullable:true
    }

    static mapping = {
        numero length: 16
    }

    @Override
    String getTipoObjeto() { 'tarjetaBancaria' }
}
