package ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos

import java.time.LocalDateTime

class OfferedOrderDto {
    var timestamp: LocalDateTime? = null
    var ticker: String? = null
    var quantity: Long? = null
    var price: Long? = null
    var arsPrice: Long? = null
    var userLastname: String? = null
    var userFirstName: String? = null
    var operationsAmount: Int? = null
    var reputation: Int? = null
}
