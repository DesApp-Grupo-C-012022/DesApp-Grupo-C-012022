package ar.edu.unq.desapp.grupoC012022.backenddesappapi.models

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.InvalidPropertyException
import java.time.LocalDateTime


class Price(var bidCurrency: Currency, sellingPrice: Long, var askCurrency: Currency) {
    val timestamp: LocalDateTime = LocalDateTime.now()

    init{
        validateSellingPrice()
    }

    var sellingPrice: Long = sellingPrice
        set(value) {
            field = value
            validateSellingPrice()
        }

    private fun validateSellingPrice() {
        if (sellingPrice < 0)
            throw InvalidPropertyException()
    }
}