package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.*
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.TransactionRepository
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
abstract class TransactionActionBase(private var transactionRepository: TransactionRepository, private var orderService: OrderService) {

//    @Autowired
//    private lateinit var transactionRepository: TransactionRepository
//    @Autowired
//    private lateinit var orderService: OrderService

    abstract fun process(order: Order, executingUser: User)

    protected fun saveTransaction(order: Order, status: Status) {
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
        transactionRepository.save(transaction)
    }

    protected fun deleteOrder(order: Order) {
        saveTransaction(order, Status.CANCELED)
        orderService.save(order)
    }
}