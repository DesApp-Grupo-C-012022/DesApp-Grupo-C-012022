package ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Operation
import java.time.LocalDateTime

class OfferedOrderDto {
    var id: Int? = null
    var operation: Operation? = null
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
