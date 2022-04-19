package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.apis.BinanceApi
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.exceptions.CurrencyNotSupportedException
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

@Service
class CurrencyService(val binanceApi: BinanceApi) {
    fun getCurrencies(referenceCurrency: String?): List<Currency> {
        return this.supportedCurrencies().map { getCurrency(it, referenceCurrency) }
    }

    fun getCurrency(currency: String, referenceCurrency: String?): Currency {
        this.validateCurrency(currency)
        val referenceCurrency = referenceCurrency ?: "USDT"
        val response = this.binanceApi.getCurrency(currency, referenceCurrency)
        val objectMapper = ObjectMapper()
        return objectMapper.readValue(response.body.toString(), Currency::class.java)
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

    private fun validateCurrency(currency: String) {
        val isCurrencySupported = this.supportedCurrencies().contains(currency)
        if (!isCurrencySupported) {
            throw CurrencyNotSupportedException()
        }
    }
}