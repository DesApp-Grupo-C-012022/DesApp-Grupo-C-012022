package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos.OrderDto
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Order
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.OrderRepository
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.InvalidPropertyException
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.UserNotFoundException
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.validators.OrderValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderService {

    @Autowired
    lateinit var orderRepository: OrderRepository
    @Autowired
    lateinit var orderValidator: OrderValidator
    @Autowired
    lateinit var priceService: PriceService
    @Autowired
    lateinit var userService: UserService

    @Throws(InvalidPropertyException::class)
    fun save(order: Order): Order {
        orderValidator.validateOrder(order)
        return orderRepository.save(order)
    }

    @Throws(InvalidPropertyException::class, UserNotFoundException::class)
    fun create(orderDto: OrderDto): Order{
        return save(fromDTO(orderDto))
    }

    @Throws(UserNotFoundException::class)
    private fun fromDTO(orderDto: OrderDto): Order {
        val price = priceService.price(orderDto.ticker, orderDto.price)

        return Order(quantity = orderDto.quantity!!,
            price = price,
            totalArsPrice = price.sellingPrice * orderDto.quantity!!,
            user = userService.getByName(orderDto.userFirstName, orderDto.userLastname),
            operation = orderDto.operation!!
        )
    }
}