package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.apis.BinanceApi
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.CurrencyRepository
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.CurrencyNotSupportedException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CurrencyService {

    @Autowired
    lateinit var binanceApi: BinanceApi
    @Autowired
    lateinit var currencyRepository: CurrencyRepository

    fun getCurrencies(): List<Currency> {
        return this.binanceApi.supportedCurrencies().map { getCurrency(it) ?: updateCurrency(it) }
    }

    fun updateCurrencies(): List<Currency> {
        return this.binanceApi.supportedCurrencies().map { updateCurrency(it) }
    }
    fun updateCurrency(currency: String): Currency {
        this.validateCurrency(currency)
        val newCurrency = this.binanceApi.getCurrency(currency, "USDT")
        val lastCurrency = getCurrency(currency)
        if (lastCurrency != null) {
            lastCurrency.latest = false
            currencyRepository.save(lastCurrency)
        }
        newCurrency.latest = true
        currencyRepository.save(newCurrency)

        return newCurrency
    }

    fun getReferenceCurrency(): Currency {
        return Currency(ticker = "USDT", usdPrice =  1.0)
    }

    fun getCurrency(currency: String): Currency? {
        return currencyRepository.findByTickerAndLatest(currency)
    }

    private fun validateCurrency(currency: String) {
        if (!this.binanceApi.isCurrencySupported(currency)) {
            throw CurrencyNotSupportedException()
        }
    }

    fun getOrUpdateCurrency(ticker: String): Currency {
        return getCurrency(ticker) ?: updateCurrency(ticker)
    }

    fun getPrices(): List<Currency> {
        return currencyRepository.findByTimestampGreaterThan(LocalDateTime.now().minusDays(1))
    }
}