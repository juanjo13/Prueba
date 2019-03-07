package mx.gob.fgjez.gebci.carpetas

import grails.databinding.BindUsing
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import mx.gob.fgjez.gebci.AccessControlledResource
import net.kaleidos.hibernate.usertype.ArrayType

@EqualsAndHashCode(includes = ['id', 'nombres', 'paterno', 'materno', 'fechaNacimiento', 'sexo', 'curp', 'role', 'estatus'])
@ToString(includes = ['id', 'nombres', 'paterno', 'materno', 'fechaNacimiento', 'sexo', 'curp', 'role', 'estatus'], includeNames = true, includePackage = false)
class Persona extends AccessControlledResource {

    String nombres
    String paterno
    String materno
    Date fechaNacimiento
    String sexo
    String[] alias

    String rol
    String estatus

    Carpeta carpeta

    Set<TarjetaBancaria> tarjetas
    Set<Celular> celulares
    Set<Vehiculo> vehiculos
    Set<Arma> armas

    static constraints = {
        authKey nullable: true
        nombres nullable: true
        paterno nullable: true
        materno nullable: true
        fechaNacimiento nullable: true
        sexo nullable: true
        alias nullable: true
        rol nullable: true
        estatus nullable: true
    }

    static mapping = {
        version false
        id generator: 'sequence', params: [sequence: 'et_persona_id_seq']
        authKey updateable: false
        nombres length: 50
        paterno length: 50
        materno length: 50
        fechaNacimiento sqlType: 'date'
        sexo sqlType: 'char', length: 1
        alias type: ArrayType, params: [type: String]
        rol length: 30
        estatus length: 30
        tarjetas  joinTable: [name: "persona_objetos_relacionados", key: "persona", column: "objeto"]
        celulares joinTable: [name: "persona_objetos_relacionados", key: "persona", column: "objeto"]
        vehiculos joinTable: [name: "persona_objetos_relacionados", key: "persona", column: "objeto"]
        armas     joinTable: [name: "persona_objetos_relacionados", key: "persona", column: "objeto"]
    }

    def getNombreCompleto() {
        [nombres ?: '', paterno ?: '', materno ?: ''].join(' ').trim()
    }

    def getNombreCompletoInverso() {
        [paterno ?: '', materno ?: '', nombres ?: ''].join(' ').trim()
    }
}
