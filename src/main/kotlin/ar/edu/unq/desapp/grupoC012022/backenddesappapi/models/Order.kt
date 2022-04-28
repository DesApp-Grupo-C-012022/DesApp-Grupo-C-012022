package ar.edu.unq.desapp.grupoC012022.backenddesappapi.models

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.InvalidPropertyException

class Order(quantity: Long, price: Price,totalArsPrice: Long, user: User, operation: Operation) {
    var quantity: Long = quantity
    var price: Price = price
    var totalArsPrice: Long = totalArsPrice
    var user: User = user
    var operation: Operation = operation

    fun validate() {
        validateQuantity()
        validateArsPrice()
    }

    private fun validateArsPrice() {
        if(totalArsPrice < 0)
            throw InvalidPropertyException()
    }

    private fun validateQuantity() {
        if (quantity < 0)
            throw InvalidPropertyException()
    }
}