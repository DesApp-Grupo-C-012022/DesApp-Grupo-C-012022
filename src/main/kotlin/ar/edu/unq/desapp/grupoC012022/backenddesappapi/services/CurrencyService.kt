package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.apis.BinanceApi
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.CurrencyNotSupportedException
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.CurrencyRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CurrencyService {

    @Autowired
    private lateinit var binanceApi: BinanceApi

    @Autowired
    private lateinit var currencyRepository: CurrencyRepository

    fun getCurrencies(referenceCurrency: String?): List<Currency> {
        var currencies = currencyRepository.findAll().toList()
        if (currencies.isEmpty()) {
            currencies = this.binanceApi.supportedCurrencies().map { getCurrency(it, referenceCurrency) }
            currencyRepository.saveAll(currencies)
        }
        return currencies
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