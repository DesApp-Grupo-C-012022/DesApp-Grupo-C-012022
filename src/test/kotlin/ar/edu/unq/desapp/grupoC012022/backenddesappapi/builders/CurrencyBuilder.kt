package ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency
import java.time.LocalDateTime

class CurrencyBuilder {
    private var currency: Currency = Currency("someId", "BTC","50000", LocalDateTime.now())

    fun build(): Currency {
        return currency
    }

    fun createCurrencyWithValues(): CurrencyBuilder {
        return this
    }

    fun createUSD(): CurrencyBuilder {
        currency = Currency("someId", "USD", "1", LocalDateTime.now())
        return this
    }
}
