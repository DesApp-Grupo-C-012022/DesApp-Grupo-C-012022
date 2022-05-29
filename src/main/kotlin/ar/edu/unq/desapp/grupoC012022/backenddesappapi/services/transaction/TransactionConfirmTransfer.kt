package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Order
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User

/**
 * The executing user is the buyer, therefore the user from the order wants to sell.
 * The order must have a SELL type.
 * Since the executing user is the buyer, the money transfer will be done FROM his
 * mercado pago CVU TO the user from the order mercado pago CVU.
 * The cripto exchange will be done FROM the user from the order wallet to the executing
 * user's wallet.
 */
class TransactionConfirmTransfer : TransactionConfirmBase() {
    override fun doProcess(order: Order, userFromOrder: User, executingUser: User) {
        // TODO: Take into consideration last cripto active price
        mercadoPagoApi.transferMoney(order.totalArsPrice, executingUser.mercadoPagoCVU, userFromOrder.mercadoPagoCVU)
        criptoExchanger.transferCriptoCurrency(order.quantity, order.price.askCurrency.ticker, userFromOrder.walletAddress, executingUser.walletAddress)
    }

}