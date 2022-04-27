package ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Operation
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Order
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Price

class OrderBuilder {
    private var order: Order = Order(1, PriceBuilder().createPriceWithValues().build(),10,UserBuilder().createUserWithValues().build(),Operation.BUY)

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
        order =  Order(1, PriceBuilder().createPriceWithValues().build(),10,UserBuilder().createUserWithValues().build(),Operation.BUY)
        return this;
    }
}
