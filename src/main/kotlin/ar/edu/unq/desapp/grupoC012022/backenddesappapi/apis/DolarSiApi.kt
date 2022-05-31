package ar.edu.unq.desapp.grupoC012022.backenddesappapi.apis

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Service
class DolarSiApi {
    private val BASE_URL = "https://www.dolarsi.com/api/api.php?type=valoresprincipales"

    fun getMepUsdToArs(): Double {
        val restTemplate = RestTemplate()
        val urlTemplate = UriComponentsBuilder.fromHttpUrl(BASE_URL).encode().toUriString()
        val body = restTemplate.getForEntity(urlTemplate, Array<Any>::class.java).body
        // Please refactor me, I was created in a rush
        return (((body!!.asList()[4] as LinkedHashMap<*,*>)["casa"] as LinkedHashMap<*,*>)["compra"] as String).replace(",", ".").toDouble()
    }
}