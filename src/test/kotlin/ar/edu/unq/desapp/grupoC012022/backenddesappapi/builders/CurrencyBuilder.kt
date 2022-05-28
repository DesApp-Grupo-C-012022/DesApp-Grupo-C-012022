package ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency

class CurrencyBuilder {
    private var currency: Currency = Currency("BTC",50000.0)

    fun build(): Currency {
        return currency;
    }

    fun createCurrencyWithValues(): CurrencyBuilder {
        return this;
    }

    fun createUSD(): CurrencyBuilder {
        currency = Currency("USD", 1.0)
        return this;
    }
}
