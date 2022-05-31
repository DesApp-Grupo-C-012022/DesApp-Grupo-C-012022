package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Order
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TransactionCancel : TransactionActionBase() {

    @Autowired
    lateinit var userService: UserService

    override fun process(order: Order, executingUser: User) {
        // Si el usuario 1 o el 2 cancela la operación, no se le suma una operación realizada y se
        // le descuenta 20 puntos de la reputación.
        // Esto es MUY RARO. Solo una orden debería poder cancelarse, no una transacción. Este tipo de transacciones
        // no son rollbackeables. Solo se permite cancelar una "transacción" si el usuario es el mismo
        // que creó la orden. Es para discutir.
        val userFromOrder = order.user
        if (executingUser.id == userFromOrder.id) {
            userFromOrder.decreaseReputationBy(20)
            userService.save(userFromOrder)
            deleteOrder(order)
        }
    }
}