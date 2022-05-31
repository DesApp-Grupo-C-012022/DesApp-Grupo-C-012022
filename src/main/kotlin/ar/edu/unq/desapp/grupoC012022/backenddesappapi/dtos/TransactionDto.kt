package ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.TransactionAction

class TransactionDto(userId: Int, orderId: Int, action: TransactionAction) {
    var userId: Int = userId // Ex.: The user who wants to buy a sell order
    var orderId: Int = orderId // Ex.: The sell order id
    var action: TransactionAction = action
}