package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.TransactionAction
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction.TransactionActionFactory
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction.TransactionCancel
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction.TransactionConfirmReception
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction.TransactionConfirmTransfer
import org.junit.jupiter.api.Test

class TransactionFactoryTest {

    private val subject = TransactionActionFactory()

    @Test
    fun confirmReceptionActionReturnsTransactionConfirmReceptionClass() {
        assert(subject.createFromAction(TransactionAction.CONFIRM_RECEPTION) is TransactionConfirmReception)
    }

    @Test
    fun confirmTransferActionReturnsTransactionConfirmTransferClass() {
        assert(subject.createFromAction(TransactionAction.CONFIRM_TRANSFER) is TransactionConfirmTransfer)
    }

    @Test
    fun cancelActionReturnsTransactionCancelClass() {
        assert(subject.createFromAction(TransactionAction.CANCEL) is TransactionCancel)
    }
}