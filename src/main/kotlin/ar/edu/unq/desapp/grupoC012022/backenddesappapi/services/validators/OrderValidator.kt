package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.validators

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Order
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.InvalidPropertyException
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.UserAlreadyExistsException
import org.springframework.stereotype.Component

@Component
class OrderValidator {

    @Throws(UserAlreadyExistsException::class, InvalidPropertyException::class)
    fun validateOrder(order: Order) {
        order.validate()
    }
}