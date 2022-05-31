package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Order
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Transaction
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.TransactionRepository
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.OrderService
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.UserService
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.CantCancelOrderThatIsNotYoursException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TransactionCancel @Autowired constructor(
    private var userService: UserService,
    transactionRepository: TransactionRepository,
    orderService: OrderService
) : TransactionActionBase(transactionRepository, orderService) {

    @Throws(CantCancelOrderThatIsNotYoursException::class)
    override fun process(order: Order, executingUser: User): Transaction {
        // Si el usuario 1 o el 2 cancela la operación, no se le suma una operación realizada y se
        // le descuenta 20 puntos de la reputación.
        val userFromOrder = order.user
        if (executingUser.id != userFromOrder.id) {
            throw CantCancelOrderThatIsNotYoursException()
        }
        userFromOrder.decreaseReputationBy(20)
        userService.save(userFromOrder)
        return deleteOrder(order)
    }
}