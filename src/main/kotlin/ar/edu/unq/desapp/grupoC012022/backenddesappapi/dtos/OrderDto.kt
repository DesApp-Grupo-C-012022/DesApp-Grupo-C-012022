package ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Operation

class OrderDto {

    var userLastname: String? = null
    var userFirstName: String? = null
    var operation: Operation? = null
    var price: Long? = null
    var ticker: String? = null
    var quantity: Long? = null
}
