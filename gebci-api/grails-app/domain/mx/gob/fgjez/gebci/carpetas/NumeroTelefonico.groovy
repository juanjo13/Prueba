package mx.gob.fgjez.gebci.carpetas

import grails.databinding.BindUsing
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import mx.gob.fgjez.gebci.AccessControlledResource

@EqualsAndHashCode(includes = ['id', 'codigoPais', 'codigoArea', 'numero', 'compania', 'rol', 'estatus', 'dispositivos'])
@ToString(includes = ['id', 'codigoPais', 'codigoArea', 'numero', 'compania', 'rol', 'estatus'], includeNames = true, includePackage = false)
class NumeroTelefonico extends AccessControlledResource {

    private static final String MX = '52'
    private static final String ladas = '33|55|81|\\d{3}'

    String codigoPais
    String codigoArea
    String numero
    String compania
    String rol
    String estatus
    Carpeta carpeta

    @BindUsing({ obj, source ->
        obj.dispositivos.clear()
        obj.dispositivos.addAll(source['dispositivos'].collect { id -> Celular.get(id as int) })
        obj.dispositivos
    })
    Set<Celular> dispositivos

    static hasMany = [
            dispositivos: Celular
    ]

    NumeroTelefonico() {
        this.codigoPais = MX
        this.dispositivos = []
    }

    static mapping = {
        version false
        id generator: 'sequence', params: [sequence:'et_numero_telefonico_id_seq']
        authKey updateable: false
        codigoPais length: 3
        codigoArea length: 6
        numero length: 15
        dispositivos joinTable: [name: "numero_celular", key: "numero_telefonico", column: "celular"]
    }

    static constraints = {
        authKey nullable: true
        codigoPais validator: { codigoPais -> codigoPais ==~ /\d+/ }
        codigoArea validator: { codigoArea, numTel ->
            if(numTel.codigoPais == MX) {
                codigoArea ==~ /${ladas}/
            } else {
                codigoArea ==~ /\d+/
            }
        }
        numero validator: { numero, numTel ->
            if(numTel.codigoPais == MX) {
                def full = numTel.codigoArea + numero
                full ==~ /\d{10}/
            } else {
                numero ==~ /\d+/
            }
        }
        compania nullable:true
        rol nullable: true
        estatus nullable: true
    }

    static NumeroTelefonico parse(String string) {
        def matcher = (string =~ /^\+(\d+)\((\d+)\)(\d+)$/)
        if (matcher) {
            return new NumeroTelefonico(
                    codigoPais: matcher[0][1],
                    codigoArea: matcher[0][2],
                    numero: matcher[0][3]
            )
        }
        def numTel
        matcher = (string =~ /^\((${ladas})\)(\d+)$/)
        if (matcher) {
            numTel = new NumeroTelefonico(
                    codigoArea: matcher[0][1],
                    numero: matcher[0][2]
            )
            if ((numTel.codigoArea + numTel.numero).length() == 10) {
                return numTel
            }
        }
        matcher = (string =~ /^(${ladas})(\d+)$/)
        if (matcher) {
            numTel = new NumeroTelefonico(
                    codigoArea: matcher[0][1],
                    numero: matcher[0][2]
            )
            if ((numTel.codigoArea + numTel.numero).length() == 10) {
                return numTel
            }
        }
        throw new IllegalArgumentException("could not parse $string as phone number")
    }

    def format() {
        if (codigoPais == MX)
            return "(${codigoArea}) ${dashedNumber(numero)}"
        else
            return "+${codigoPais}${noPadding(codigoArea)}${numero}"
    }

    private static def dashedNumber(String number) {
        splitNumber(number).join('-')
    }

    private static def splitNumber(String number) {
        if (number.length() < 5)
            return [number]
        else if (number.length() < 8)
            return [number.substring(0,3), number.substring(3)]
        else if (number.length() == 8)
            return [number.substring(0,4), number.substring(4)]
        else if (number.length() < 10)
            return [number.substring(0,3), number.substring(3,6), number.substring(6)]
        else {
            return [number.substring(0, 3), number.substring(3, 7), number.substring(7)]
        }
    }

    private static def noPadding(String number) {
        number.replaceFirst('^0+(?!$)', '')
    }

}
