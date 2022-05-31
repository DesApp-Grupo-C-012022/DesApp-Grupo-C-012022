package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos.OfferedOrderDto
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos.OrderDto
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Order
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.OrderRepository
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.InvalidPropertyException
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.OrderNotFoundException
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
        order.price = priceService.save(order.price)
        return orderRepository.save(order)
    }

    @Throws(InvalidPropertyException::class, UserNotFoundException::class)
    fun create(orderDto: OrderDto): Order{
        return save(fromDTO(orderDto))
    }

    fun delete(order: Order) {
        orderRepository.delete(order)
    }

    @Throws(OrderNotFoundException::class)
    fun findById(id: Int): Order {
        return orderRepository.findById(id).orElseThrow { OrderNotFoundException() }
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

    fun getActives(): List<OfferedOrderDto> {
        return orderRepository.findByIsActive(true).map{ o -> toOfferedDto(o)}
    }

    fun toOfferedDto(order: Order): OfferedOrderDto {
        var orderDto = OfferedOrderDto()
        orderDto.timestamp = order.price.timestamp
        orderDto.ticker = order.price.bidCurrency.ticker
        orderDto.quantity = order.quantity
        orderDto.price = order.price.sellingPrice
        orderDto.arsPrice = order.totalArsPrice
        orderDto.userFirstName = order.user.firstName
        orderDto.userLastname = order.user.lastName
        orderDto.operationsAmount = order.user.operationsAmount
        orderDto.reputation = order.user.reputation

        return orderDto
    }
}