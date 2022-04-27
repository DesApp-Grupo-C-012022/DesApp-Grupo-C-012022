package ar.edu.unq.desapp.grupoC012022.backenddesappapi.model

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.OrderBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OrderTests {
    val orderBuilder: OrderBuilder = OrderBuilder()

    @Test
    fun orderCreation() {
        val order = orderBuilder.createOrderWithValues().build()
        Assertions.assertNotNull(order.quantity)
        Assertions.assertNotNull(order.price)
        Assertions.assertNotNull(order.totalArsPrice)
        Assertions.assertNotNull(order.operation)
        Assertions.assertNotNull(order.user)
    }

    @Test
    fun validatingOrderWithNegativeQuantityThrowsException() {
        assertThrows<IllegalArgumentException> {orderBuilder.createOrderWithValues().quantity(-5).build().validate()}
    }

    @Test
    fun validatingOrderWithNegativeTotalArsPriceThrowsException() {
        assertThrows<IllegalArgumentException> {orderBuilder.createOrderWithValues().totalArsPrice(-5).build().validate()}
    }

    @Test
    fun validatingOrderWithQuantity0DoesntThrowException() {
        Assertions.assertDoesNotThrow { orderBuilder.createOrderWithValues().quantity(0).build().validate()}
    }

    @Test
    fun validatingOrderWithTotalArsPrice0DoesntThrowException() {
        Assertions.assertDoesNotThrow { orderBuilder.createOrderWithValues().totalArsPrice(0).build().validate()}
    }

    @Test
    fun validatingOrderWithPositiveQuantityDoesntThrowException() {
        Assertions.assertDoesNotThrow { orderBuilder.createOrderWithValues().quantity(1).build().validate()}
    }

    @Test
    fun validatingOrderWithPositiveTotalArsPriceDoesntThrowException() {
        Assertions.assertDoesNotThrow { orderBuilder.createOrderWithValues().totalArsPrice(1).build().validate()}
    }
}