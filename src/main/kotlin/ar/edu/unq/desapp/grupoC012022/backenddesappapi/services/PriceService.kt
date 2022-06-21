package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Price
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.PriceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PriceService {

    @Autowired
    lateinit var currencyService: CurrencyService
    @Autowired
    lateinit var priceRepository: PriceRepository

    @Transactional
    fun save(price: Price): Price {
        return priceRepository.save(price)
    }

    fun price(ticker: String?, price: Long?): Price {
        return Price(bidCurrency = currencyService.getOrUpdateCurrency(ticker!!), sellingPrice = price!!, askCurrency = currencyService.getReferenceCurrency())
    }
}