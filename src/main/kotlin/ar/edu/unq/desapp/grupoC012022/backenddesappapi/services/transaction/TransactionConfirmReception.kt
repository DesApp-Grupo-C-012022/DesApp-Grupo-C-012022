package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Order
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User
import org.springframework.stereotype.Component

/**
 * The user from the order wants to buy.
 * The order must have a BUY type.
 * Since the user from the order is the buyer, the money transfer will be done FROM his
 * mercado pago CVU TO the executing user mercado pago CVU.
 * The cripto exchange will be done FROM the executing user wallet to the user from the order
 * wallet.
 */

@Component
class TransactionConfirmReception : TransactionConfirmBase() {
    override fun doProcess(order: Order, userFromOrder: User, executingUser: User) {
        // TODO: Take into consideration last cripto active price
        transferMoney(order.totalArsPrice, userFromOrder.mercadoPagoCVU, executingUser.mercadoPagoCVU)
        transferCriptoCurrency(order.quantity, order.price.askCurrency.ticker, executingUser.walletAddress, userFromOrder.walletAddress)
    }
}