package ar.edu.unq.desapp.grupoC012022.backenddesappapi.models

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.TransactionBuilder
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.exceptions.InvalidPropertyException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TransactionTest {
    private val transactionBuilder: TransactionBuilder = TransactionBuilder()

    @Test
    fun transactionCreation() {
        val transaction = transactionBuilder.createTransactionWithValues().build()
        Assertions.assertNotNull(transaction.amount)
        Assertions.assertNotNull(transaction.currency)
        Assertions.assertNotNull(transaction.quantity)
        Assertions.assertNotNull(transaction.price)
        Assertions.assertNotNull(transaction.user)
        Assertions.assertNotNull(transaction.destinationAddress)
        Assertions.assertNotNull(transaction.operationQuantity)
        Assertions.assertNotNull(transaction.status)
        Assertions.assertNotNull(transaction.timestamp)
    }

    @Test
    fun validatingTransactionWithNegativeAmountThrowsException() {
        assertThrows<InvalidPropertyException> {transactionBuilder.createTransactionWithValues().amount(-5).build().validate()}
    }

    @Test
    fun validatingTransactionWithAmount0DoesntThrowException() {
        Assertions.assertDoesNotThrow { transactionBuilder.createTransactionWithValues().amount(0).build().validate()}
    }

    @Test
    fun validatingTransactionWithPositiveAmountDoesntThrowException() {
        Assertions.assertDoesNotThrow { transactionBuilder.createTransactionWithValues().amount(1).build().validate()}
    }

    @Test
    fun validatingTransactionWithNegativeQuantityThrowsException() {
        assertThrows<InvalidPropertyException> {transactionBuilder.createTransactionWithValues().quantity(-5).build().validate()}
    }

    @Test
    fun validatingTransactionWithQuantity0DoesntThrowException() {
        Assertions.assertDoesNotThrow { transactionBuilder.createTransactionWithValues().quantity(0).build().validate()}
    }

    @Test
    fun validatingTransactionWithPositiveQuantityDoesntThrowException() {
        Assertions.assertDoesNotThrow { transactionBuilder.createTransactionWithValues().quantity(1).build().validate()}
    }

    @Test
    fun validatingTransactionWithEmptyDestinationAddressThrowsException() {
        assertThrows<InvalidPropertyException> {transactionBuilder.createTransactionWithValues().destinationAddress("").build().validate()}
    }

    @Test
    fun validatingTransactionWithADestinationAddressDoesntThrowException() {
        Assertions.assertDoesNotThrow { transactionBuilder.createTransactionWithValues().destinationAddress("Address8888").build().validate()}
    }

    @Test
    fun validatingTransactionWithNegativeOperationQuantityThrowsException() {
        assertThrows<InvalidPropertyException> {transactionBuilder.createTransactionWithValues().operationQuantity(-5).build().validate()}
    }

    @Test
    fun validatingTransactionWithOperationQuantity0DoesntThrowException() {
        Assertions.assertDoesNotThrow { transactionBuilder.createTransactionWithValues().operationQuantity(0).build().validate()}
    }

    @Test
    fun validatingTransactionWithPositiveOperationQuantityDoesntThrowException() {
        Assertions.assertDoesNotThrow { transactionBuilder.createTransactionWithValues().operationQuantity(1).build().validate()}
    }
}