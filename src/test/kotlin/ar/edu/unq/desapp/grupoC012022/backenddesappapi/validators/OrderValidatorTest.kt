package ar.edu.unq.desapp.grupoC012022.backenddesappapi.validators

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.OrderBuilder
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.InvalidPropertyException
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.validators.OrderValidator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OrderValidatorTest {

    private val orderBuilder = OrderBuilder()
    private val orderValidator = OrderValidator()

    @Test
    fun validatingOrderWithNegativeQuantityThrowsException() {
        assertThrows<InvalidPropertyException> {orderValidator.validateOrder(orderBuilder.createOrderWithValues().quantity(-5).build())}
    }

    @Test
    fun validatingOrderWithNegativeTotalArsPriceThrowsException() {
        assertThrows<InvalidPropertyException> {orderValidator.validateOrder(orderBuilder.createOrderWithValues().totalArsPrice(-5).build())}
    }

    @Test
    fun validatingOrderWithQuantity0DoesntThrowException() {
        Assertions.assertDoesNotThrow {orderValidator.validateOrder(orderBuilder.createOrderWithValues().quantity(0).build())}
    }

    @Test
    fun validatingOrderWithTotalArsPrice0DoesntThrowException() {
        Assertions.assertDoesNotThrow {orderValidator.validateOrder(orderBuilder.createOrderWithValues().totalArsPrice(0).build())}
    }

    @Test
    fun validatingOrderWithPositiveQuantityDoesntThrowException() {
        Assertions.assertDoesNotThrow {orderValidator.validateOrder(orderBuilder.createOrderWithValues().quantity(1).build())}
    }

    @Test
    fun validatingOrderWithPositiveTotalArsPriceDoesntThrowException() {
        Assertions.assertDoesNotThrow {orderValidator.validateOrder(orderBuilder.createOrderWithValues().totalArsPrice(1).build())}
    }
}