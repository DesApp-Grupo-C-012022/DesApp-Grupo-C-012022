package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.apis.BinanceApi
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.CurrencyRepository
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.exceptions.CurrencyNotSupportedException
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class CurrencyService {

    @Autowired
    lateinit var binanceApi: BinanceApi
    @Autowired
    lateinit var currencyRepository: CurrencyRepository

    @Transactional
    @CachePut(key = "'allCurrencies'", value = ["allCurrenciesCache"])
    fun updateCurrencies(): List<Currency> {
        return this.binanceApi.supportedCurrencies().map { updateCurrency(it) }
    }

    @Transactional
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

    @Transactional
    fun getOrUpdateCurrency(ticker: String): Currency {
        return getCurrency(ticker) ?: updateCurrency(ticker)
    }

    @Cacheable(key = "'allCurrencies'", value = ["allCurrenciesCache"])
    fun getCurrencies(): List<Currency> {
        return this.binanceApi.supportedCurrencies().map { getOrUpdateCurrency(it) }
    }

    fun getReferenceCurrency(): Currency {
        return Currency(ticker = "USDT", usdPrice =  1.0)
    }

    fun getPrices(ticker: String): List<Currency> {
        return currencyRepository.findByTickerAndTimestampGreaterThanOrderByTickerAscTimestampDesc(ticker, LocalDateTime.now().minusDays(1))
    }

    private fun getCurrency(currency: String): Currency? {
        LogManager.getLogger().info("Getting currency $currency from db")
        val ticker = currency + "USDT"
        return currencyRepository.findFirstByTickerOrderByTimestampDesc(ticker)
    }

    private fun validateCurrency(currency: String) {
        if (!this.binanceApi.isCurrencySupported(currency)) {
            throw CurrencyNotSupportedException()
        }
    }
}