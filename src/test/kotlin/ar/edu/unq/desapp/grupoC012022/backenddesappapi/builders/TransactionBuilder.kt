package ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.*

class TransactionBuilder {
    private var transaction: Transaction = Transaction(CurrencyBuilder().createCurrencyWithValues().build(),1,PriceBuilder().createPriceWithValues().build(),1,UserBuilder().createUserWithValues().build(),1,"Address1",Status.PENDING)

    fun currency(currency: Currency): TransactionBuilder {
        transaction.currency = currency
        return this
    }

    fun quantity(quantity: Long): TransactionBuilder {
        transaction.quantity = quantity
        return this
    }

    fun price(price: Price): TransactionBuilder {
        transaction.price = price
        return this
    }

    fun amount(amount: Long): TransactionBuilder {
        transaction.amount = amount
        return this
    }

    fun user(user: User): TransactionBuilder {
        transaction.user = user
        return this
    }

    fun destinationAddress(destinationAddress: String): TransactionBuilder {
        transaction.destinationAddress = destinationAddress
        return this
    }

    fun operationQuantity(operationQuantity: Long): TransactionBuilder {
        transaction.operationQuantity = operationQuantity
        return this
    }

    fun status(status: Status): TransactionBuilder {
        transaction.status = status
        return this
    }


    fun build(): Transaction {
        return transaction;
    }

    fun createTransactionWithValues(): TransactionBuilder {
        transaction =  Transaction(CurrencyBuilder().createCurrencyWithValues().build(),1,PriceBuilder().createPriceWithValues().build(),1,UserBuilder().createUserWithValues().build(),1,"Address1",Status.PENDING)
        return this;
    }
}