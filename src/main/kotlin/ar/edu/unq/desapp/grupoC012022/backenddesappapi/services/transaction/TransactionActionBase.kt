package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.*
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.TransactionRepository
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.OrderService
import org.springframework.stereotype.Component

@Component
abstract class TransactionActionBase(private var transactionRepository: TransactionRepository, private var orderService: OrderService) {

    abstract fun process(order: Order, executingUser: User): Transaction

    protected fun saveTransaction(order: Order, status: Status): Transaction {
        order.isActive = false
        val destionationAddress = if (order.operation == Operation.BUY) order.user.walletAddress else order.user.mercadoPagoCVU
        val transaction = Transaction(
            order.price.bidCurrency,
            order.quantity,
            order.price,
            order.totalArsPrice,
            order.user,
            order.user.operationsAmount!!,
            destionationAddress,
            status
        )
        return transactionRepository.save(transaction)
    }

    protected fun deleteOrder(order: Order): Transaction {
        val transaction = saveTransaction(order, Status.CANCELED)
        orderService.save(order)
        return transaction
    }
}