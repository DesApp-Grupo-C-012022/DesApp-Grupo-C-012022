package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Price
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PriceService {

    @Autowired
    lateinit var currencyService: CurrencyService

    fun price(ticker: String?, price: Long?): Price {
        return Price(bidCurrency = currencyService.getCurrency(ticker!!), sellingPrice = price!!, askCurrency = currencyService.getReferenceCurrency())
    }

}