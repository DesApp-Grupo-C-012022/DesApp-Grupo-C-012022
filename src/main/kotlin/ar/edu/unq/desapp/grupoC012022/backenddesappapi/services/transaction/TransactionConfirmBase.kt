package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Order
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Transaction
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.TransactionRepository
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.CurrencyService
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.OrderService
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.UserService
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.CancelOrderDuePriceDifferenceException
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.CantBuyYourOwnOrderException
import java.time.LocalDateTime
import org.springframework.stereotype.Component
import java.time.Duration
import kotlin.math.abs

@Component
abstract class TransactionConfirmBase(
    private var userService: UserService,
    protected var currencyService: CurrencyService,
    private var mercadoPagoApi: MercadoPagoApi,
    private var criptoExchanger: CriptoExchanger,
    transactionRepository: TransactionRepository,
    orderService: OrderService
) : TransactionActionBase(transactionRepository, orderService) {

    protected abstract fun doProcess(order: Order, executingUser: User): Transaction
    protected abstract fun checkBidCurrencyVariation(order: Order)
    protected abstract fun checkActionAgainstOrderAction(order: Order)

    override fun process(order: Order, executingUser: User): Transaction {
        try {
            checkUsersIdsConsistency(order.user, executingUser)
            checkActionAgainstOrderAction(order)
            checkBidCurrencyVariation(order)
        } catch (e: CancelOrderDuePriceDifferenceException) {
            return deleteOrder(order)
        }
        checkOrderTimestamp(order, executingUser)
        return doProcess(order, executingUser)
    }

    protected fun transferMoney(totalAmountArs: Long, fromMercadoPagoCvu: String, toMercadoPagoCvu: String) {
        mercadoPagoApi.transferMoney(totalAmountArs, fromMercadoPagoCvu, toMercadoPagoCvu)
    }

    protected fun transferCriptoCurrency(totalAmountCriptoCurrency: Long, criptoActive: String, fromWallet: String, toWallet: String) {
        criptoExchanger.transferCriptoCurrency(totalAmountCriptoCurrency, criptoActive, fromWallet, toWallet)
    }

    @Throws(CantBuyYourOwnOrderException::class)
    private fun checkUsersIdsConsistency(userFromOrder: User, executingUser: User) {
        if (userFromOrder.id == executingUser.id) {
            throw CantBuyYourOwnOrderException()
        }
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