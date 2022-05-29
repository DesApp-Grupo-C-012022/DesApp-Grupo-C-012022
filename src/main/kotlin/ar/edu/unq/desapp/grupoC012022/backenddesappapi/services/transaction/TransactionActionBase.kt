package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.*
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.TransactionRepository
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
abstract class TransactionActionBase {

    @Autowired
    private lateinit var transactionRepository: TransactionRepository
    @Autowired
    private lateinit var orderService: OrderService

    abstract fun process(order: Order, executingUser: User)

    protected fun saveTransaction(order: Order, status: Status) {
        val destionationAddress = if (order.operation == Operation.BUY) order.user.walletAddress else order.user.mercadoPagoCVU
        val transaction = Transaction(
            order.price.bidCurrency,
            order.quantity, // Que quantity usamos acá?????
            order.price,
            order.totalArsPrice,
            order.user, // Nunca se guarda el usuario que ejecuta la orden? Hay que crear otra orden opuesta y por ende haber 2 transacciones?
            order.quantity,
            destionationAddress,
            status
        )
        transactionRepository.save(transaction)
    }

    protected fun deleteOrder(order: Order) {
        saveTransaction(order, Status.CANCELED)
        orderService.delete(order)
    }
}