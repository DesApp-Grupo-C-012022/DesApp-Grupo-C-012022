package ar.edu.unq.desapp.grupoC012022.backenddesappapi.apis

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class BinanceApi {
    private val BASE_URL = "https://api1.binance.com/api/v3/ticker/price"

    fun getCurrency(currency: String, referenceCurrency: String): Currency {
        val restTemplate = RestTemplate()
        val urlTemplate = UriComponentsBuilder.fromHttpUrl(BASE_URL)
            .queryParam("symbol", "$currency$referenceCurrency")
            .encode()
            .toUriString()
        val response = restTemplate.getForEntity(urlTemplate, String::class.java)
        return ObjectMapper().readValue(response.body.toString(), Currency::class.java)
    }

    fun supportedCurrencies(): List<String> {
        return listOf(
            "ALICE",
            "MATIC",
            "AXS",
            "AAVE",
            "ATOM",
            "NEO",
            "DOT",
            "ETH",
            "CAKE",
            "BTC",
            "BNB",
            "ADA",
            "TRX",
            "AUDIO"
        )
    }

    fun isCurrencySupported(currency: String) : Boolean {
        return this.supportedCurrencies().contains(currency)
    }
}