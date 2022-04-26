package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.apis.BinanceApi
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.CurrencyNotSupportedException
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CurrencyService {

    @Autowired
    private lateinit var binanceApi: BinanceApi

    fun getCurrencies(referenceCurrency: String?): List<Currency> {
        return this.binanceApi.supportedCurrencies().map { getCurrency(it, referenceCurrency) }
    }

    fun getCurrency(currency: String, referenceCurrency: String?): Currency {
        this.validateCurrency(currency)
        val referenceCurrency = referenceCurrency ?: "USDT"
        val response = this.binanceApi.getCurrency(currency, referenceCurrency)
        val objectMapper = ObjectMapper()
        return objectMapper.readValue(response.body.toString(), Currency::class.java)
    }

    private fun validateCurrency(currency: String) {
        if (!this.binanceApi.isCurrencySupported(currency)) {
            throw CurrencyNotSupportedException()
        }
    }
}