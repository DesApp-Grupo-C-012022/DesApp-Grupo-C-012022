package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.apis.BinanceApi
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.CurrencyRepository
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.exceptions.CurrencyNotSupportedException
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class CurrencyService {

    @Autowired
    lateinit var binanceApi: BinanceApi
    @Autowired
    lateinit var currencyRepository: CurrencyRepository
    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, Currency>

    @Transactional
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

        LogManager.getLogger().info("Saving currency ${newCurrency.ticker} to redis")
        this.redisTemplate.opsForValue().set("ticker_${newCurrency.ticker}", newCurrency)

        return newCurrency
    }

    @Transactional
    fun getOrUpdateCurrency(ticker: String): Currency {
        return getCurrency(ticker) ?: updateCurrency(ticker)
    }

    fun getCurrencies(): List<Currency> {
        return this.binanceApi.supportedCurrencies().map { getOrUpdateCurrency(it) }
    }

    fun getReferenceCurrency(): Currency {
        return Currency(ticker = "USDT", usdPrice =  1.0)
    }

    fun getPrices(ticker: String): List<Currency> {
        return currencyRepository.findByTickerAndTimestampGreaterThanOrderByTickerAscTimestampDesc(ticker, LocalDateTime.now().minusDays(1))
    }

    private fun getCurrency(ticker: String): Currency? {
        LogManager.getLogger().info("Getting currency $ticker from redis")
        var currency: Currency? = this.redisTemplate.opsForValue().get("ticker_${ticker}USDT")
        if (currency == null) {
            LogManager.getLogger().info("Getting currency $ticker from db")
            currency = currencyRepository.findFirstByTickerOrderByTimestampDesc("${ticker}USDT")
        }
        return currency
    }

    private fun validateCurrency(currency: String) {
        if (!this.binanceApi.isCurrencySupported(currency)) {
            throw CurrencyNotSupportedException()
        }
    }
}