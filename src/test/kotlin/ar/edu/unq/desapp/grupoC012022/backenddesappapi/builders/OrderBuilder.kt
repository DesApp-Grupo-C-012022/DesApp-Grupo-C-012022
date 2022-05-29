package ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos.OrderDto
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Operation
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Order
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Price

class OrderBuilder {
    private var order: Order = Order(quantity = 1, price = PriceBuilder().createPriceWithValues().build(), totalArsPrice = 10,user = UserBuilder().createUserWithValues().build(), operation =Operation.BUY)

    fun id(id: Int): OrderBuilder {
        order.id = id
        return this
    }

    fun quantity(quantity: Long): OrderBuilder {
        order.quantity = quantity
        return this
    }

    fun price(price: Price): OrderBuilder {
        order.price = price
        return this
    }

    fun totalArsPrice(totalArsPrice: Long): OrderBuilder {
        order.totalArsPrice = totalArsPrice
        return this
    }

    fun operation(operation: Operation): OrderBuilder {
        order.operation = operation
        return this
    }

    fun build(): Order {
        return order;
    }

    fun createOrderWithValues(): OrderBuilder {
        order =  Order(quantity = 1, price = PriceBuilder().createPriceWithValues().build(), totalArsPrice =  10, user =  UserBuilder().createUserWithValues().build(),operation = Operation.BUY)
        return this;
    }

    fun buildDto(): OrderDto {
        var orderDto = OrderDto()
        orderDto.price = order.price.sellingPrice
        orderDto.operation = order.operation
        orderDto.quantity = order.quantity
        orderDto.ticker = order.price.askCurrency.ticker
        orderDto.userFirstName = order.user.firstName
        orderDto.userLastname = order.user.lastName

        return orderDto
    }
}
