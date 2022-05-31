package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Order
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.CurrencyService
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.UserService
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.CancelOrderDuePriceDifferenceException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.abs

@Component
abstract class TransactionConfirmBase : TransactionActionBase() {

    @Autowired
    private lateinit var userService: UserService
    @Autowired
    protected lateinit var currencyService: CurrencyService
    @Autowired
    protected lateinit var mercadoPagoApi: MercadoPagoApi
    @Autowired
    protected lateinit var criptoExchanger: CriptoExchanger

    protected abstract fun doProcess(order: Order, executingUser: User)
    protected abstract fun checkBidCurrencyVariation(order: Order)

    override fun process(order: Order, executingUser: User) {
        try {
            checkBidCurrencyVariation(order)
        } catch (e: CancelOrderDuePriceDifferenceException) {
            return
        }
        checkOrderTimestamp(order, executingUser)
        doProcess(order, executingUser)
    }

    protected fun transferMoney(totalAmountArs: Long, fromMercadoPagoCvu: String, toMercadoPagoCvu: String) {
        mercadoPagoApi.transferMoney(totalAmountArs, fromMercadoPagoCvu, toMercadoPagoCvu)
    }

    protected fun transferCriptoCurrency(totalAmountCriptoCurrency: Long, criptoActive: String, fromWallet: String, toWallet: String) {
        criptoExchanger.transferCriptoCurrency(totalAmountCriptoCurrency, criptoActive, fromWallet, toWallet)
    }

    private fun checkOrderTimestamp(order: Order, executingUser: User) {
        val diff = abs(Duration.between(LocalDateTime.now(), order.price.timestamp).toMinutes())
        val reputationAmountIncrease = if (diff <= 30) 10 else 5
        val userFromOrder = order.user
        userFromOrder.increaseReputationBy(reputationAmountIncrease)
        userFromOrder.increaseOperationsAmount()
        executingUser.increaseReputationBy(reputationAmountIncrease)
        executingUser.increaseOperationsAmount()
        userService.save(userFromOrder)
        userService.save(executingUser)
    }
}