package gebci.api

class UrlMappings {

    static mappings = {
        "/api/bancos"(resources: "banco")
        "/api/delitos"(resources: "delito")
        "/api/procedencias"(resources: "procedencia")
        "/api/usuarios"(controller: "user", action: 'index')
        "/api/usuarios/mps"(controller: "user", action: 'ministeriosPublicos')

        "/api/carpetas"(resources: "carpeta") {
            "/personas" (resources: "persona") {
                "/lookup" (controller: "persona", action: "lookupStatus", method: "GET")
                "/lookup" (controller: "persona", action: "lookupSubscribe", method: "POST")
            }
            "/telefonos" (resources: "numeroTelefonico")
            "/celulares" (resources: "celular")
            "/tarjetas" (resources: "tarjetaBancaria")
            "/armas" (resources: "arma")
            "/vehiculos" (resources: "vehiculo")
            "/regiones" (resources: "region")
            "/domicilios" (resources: "domicilio")
            "/llamadas" (resources: "llamadaTelefonica")
            "/sucesos" (resources: "suceso")
			"/huellas" (resources: "huella")
			"/rostros" (resources: "rostro")
        }

        "/api/search"(controller: "search", action:"search", method: "POST")
        "/api/search"(controller: "search", action:"searchElements", method: "GET")

        "/api/match/notify"(controller: "match", action:"notification", method: "POST")

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
        "403"(view: '/forbidden')
    }
}
