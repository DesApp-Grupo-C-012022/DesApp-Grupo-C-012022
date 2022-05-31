package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos.TransactionDto
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.OrderService
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TransactionService {

    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var orderService: OrderService
    @Autowired
    lateinit var transactionActionFactory: TransactionActionFactory

    fun processTransaction(transaction: TransactionDto) {
        val order = orderService.findById(transaction.orderId)
        val executingUser = userService.findById(transaction.userId)
        transactionActionFactory.createFromAction(transaction.action).process(order, executingUser)
    }
}

@Service
class MercadoPagoApi {
    fun transferMoney(totalAmountArs: Long, fromMercadoPagoCvu: String, toMercadoPagoCvu: String) {
        // do stuff
    }
}

@Service
class CriptoExchanger {
    fun transferCriptoCurrency(totalAmountCriptoCurrency: Long, criptoActive: String, fromWallet: String, toWallet: String) {
        // do stuff
    }
}