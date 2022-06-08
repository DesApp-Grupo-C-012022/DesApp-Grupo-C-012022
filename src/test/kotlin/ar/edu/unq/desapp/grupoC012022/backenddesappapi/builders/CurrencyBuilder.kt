package ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency

class CurrencyBuilder {
    private var currency: Currency = Currency(ticker =  "BTC", usdPrice =  50000.0)

    fun build(): Currency {
        return currency;
    }

    fun createCurrencyWithValues(): CurrencyBuilder {
        currency = Currency(ticker =  "BTC", usdPrice =  50000.0)
        return this;
    }

    fun ticker(ticker: String): CurrencyBuilder {
        currency.ticker = ticker
        return this
    }

    fun usdPrice(usdPrice: Double): CurrencyBuilder {
        currency.usdPrice = usdPrice
        return this
    }

    fun createUSD(): CurrencyBuilder {
        currency = Currency(ticker =  "USD", usdPrice =  1.0)
        return this;
    }
}
