package ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Operation
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Price

class OrderSavedDto(
    var id: Int,
    var quantity: Long,
    var price: Price,
    var totalArsPrice: Long,
    var user: DeserializableUser,
    var operation: Operation,
    var isActive: Boolean = true,
)