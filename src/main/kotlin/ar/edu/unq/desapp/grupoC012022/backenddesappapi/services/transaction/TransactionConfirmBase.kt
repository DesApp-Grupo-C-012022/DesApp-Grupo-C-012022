package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Order
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.UserService
import org.joda.time.LocalDateTime
import org.joda.time.Minutes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
abstract class TransactionConfirmBase : ITransactionAction {

    @Autowired
    private lateinit var userService: UserService
    @Autowired
    protected lateinit var mercadoPagoApi: MercadoPagoApi
    @Autowired
    protected lateinit var criptoExchanger: CriptoExchanger

    protected abstract fun doProcess(order: Order, userFromOrder: User, executingUser: User)

    override fun process(order: Order, userFromOrder: User, executingUser: User) {
        checkOrderTimestamp(order, userFromOrder, executingUser)
        doProcess(order, userFromOrder, executingUser)
    }

    protected fun transferMoney(totalAmountArs: Long, fromMercadoPagoCvu: String, toMercadoPagoCvu: String) {
        mercadoPagoApi.transferMoney(totalAmountArs, fromMercadoPagoCvu, toMercadoPagoCvu)
    }

    protected fun transferCriptoCurrency(totalAmountCriptoCurrency: Long, criptoActive: String, fromWallet: String, toWallet: String) {
        criptoExchanger.transferCriptoCurrency(totalAmountCriptoCurrency, criptoActive, fromWallet, toWallet)
    }

    private fun checkOrderTimestamp(order: Order, userFromOrder: User, executingUser: User) {
        val diff = Minutes.minutesBetween(LocalDateTime.now(), order.price.timestamp).minutes
        val reputationAmountIncrease = if (diff <= 30) 10 else 5
        userFromOrder.increaseReputationBy(reputationAmountIncrease)
        executingUser.increaseReputationBy(reputationAmountIncrease)
        userService.save(userFromOrder)
        userService.save(executingUser)
    }
}