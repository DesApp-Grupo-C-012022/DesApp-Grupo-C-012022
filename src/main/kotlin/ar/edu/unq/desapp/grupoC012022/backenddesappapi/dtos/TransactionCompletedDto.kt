package ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.*

class TransactionCompletedDto(
    var currency: Currency,
    var quantity: Long,
    var price: Price,
    var amount: Long,
    var userFirstName: String,
    var userLastName: String,
    var operationQuantity: Int,
    var destinationAddress: String,
    var status: Status
)