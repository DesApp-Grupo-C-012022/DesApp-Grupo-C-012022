package ar.edu.unq.desapp.grupoC012022.backenddesappapi.models

import java.time.LocalDateTime

class Transaction(currency: Currency, quantity: Long, price: Price, amount: Long, user: User, operationQuantity: Long, destinationAddress: String, status: Status) {
    var currency: Currency = currency
    var quantity: Long = quantity
    var price: Price = price
    var amount: Long = amount
    var user: User = user
    var operationQuantity: Long = operationQuantity // Cantidad nominal de cryptoActivo
    var destinationAddress: String = destinationAddress // TODO: Analizar si tiene sentido modelar Address
    var status: Status = status
    val timestamp: LocalDateTime = LocalDateTime.now()

    fun validate() {
        validateQuantity()
        validateAmount()
        validateOperationQuantity()
        validateDestiantionAddress()
    }

    private fun validateQuantity() {
        if(quantity < 0)
        // TODO: Actualizar excepcion lanzada
            throw IllegalArgumentException()
    }

    private fun validateAmount() {
        if(amount < 0)
        // TODO: Actualizar excepcion lanzada
            throw IllegalArgumentException()
    }

    private fun validateOperationQuantity() {
        if(operationQuantity < 0)
        // TODO: Actualizar excepcion lanzada
            throw IllegalArgumentException()
    }

    private fun validateDestiantionAddress() {
        if(destinationAddress.isEmpty())
        // TODO: Actualizar excepcion lanzada
            throw IllegalArgumentException()
    }
}