package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos.TransactionDto
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.OrderRepository
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TransactionService {

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var orderRepository: OrderRepository
    @Autowired
    lateinit var transactionActionFactory: TransactionActionFactory

    fun processTransaction(transaction: TransactionDto) {
        val order = orderRepository.findById(transaction.orderId).get()
        val executingUser = userRepository.findById(transaction.userId).get()
        val userFromOrder = order.user
        transactionActionFactory.createFromAction(transaction.action).process(order, userFromOrder, executingUser)
    }
}

@Service
class MercadoPagoApi {
    fun transferMoney(totalAmountArs: Long, fromMercadoPagoCvu: String, toMercadoPagoCvu: String) {
        // do stuff I don't care
    }
}

@Service
class CriptoExchanger {
    fun transferCriptoCurrency(totalAmountCriptoCurrency: Long, criptoActive: String, fromWallet: String, toWallet: String) {
        // do stuff I don't care
    }
}