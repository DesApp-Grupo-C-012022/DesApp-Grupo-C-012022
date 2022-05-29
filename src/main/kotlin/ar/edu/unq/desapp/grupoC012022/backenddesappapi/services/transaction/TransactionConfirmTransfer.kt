package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Order
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User
import org.springframework.stereotype.Component

/**
 * The executing user is the buyer, therefore the user from the order wants to sell.
 * The order must have a SELL type.
 * Since the executing user is the buyer, the money transfer will be done FROM his
 * mercado pago CVU TO the user from the order mercado pago CVU.
 * The cripto exchange will be done FROM the user from the order wallet to the executing
 * user's wallet.
 */

@Component
class TransactionConfirmTransfer : TransactionConfirmBase() {
    override fun doProcess(order: Order, executingUser: User) {
        // TODO: Take into consideration last cripto active price
        transferMoney(order.totalArsPrice, executingUser.mercadoPagoCVU, order.user.mercadoPagoCVU)
        transferCriptoCurrency(order.quantity, order.price.askCurrency.ticker, order.user.walletAddress, executingUser.walletAddress)
    }
}