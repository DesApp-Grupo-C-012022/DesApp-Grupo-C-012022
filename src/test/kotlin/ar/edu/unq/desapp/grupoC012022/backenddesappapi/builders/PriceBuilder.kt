package ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Price
import org.joda.time.LocalDateTime

class PriceBuilder {
    private var price: Price = Price(askCurrency = CurrencyBuilder().createCurrencyWithValues().build(), sellingPrice = 100, bidCurrency =  CurrencyBuilder().createCurrencyWithValues().build())

    fun bidCurrency(bidCurrency: Currency): PriceBuilder {
        price.bidCurrency = bidCurrency
        return this
    }

    fun askCurrency(askCurrency: Currency): PriceBuilder {
        price.askCurrency = askCurrency
        return this
    }

    fun sellingPrice(sellingPrice: Long): PriceBuilder {
        price.sellingPrice = sellingPrice
        return this
    }

    fun timestamp(timestamp: LocalDateTime): PriceBuilder {
        price.timestamp = timestamp
        return this
    }

    fun build(): Price {
        return price;
    }

    fun createPriceWithValues(): PriceBuilder {
        price = Price(askCurrency =  CurrencyBuilder().createCurrencyWithValues().build(), sellingPrice =  100, bidCurrency =  CurrencyBuilder().createCurrencyWithValues().build())
        return this;
    }
}