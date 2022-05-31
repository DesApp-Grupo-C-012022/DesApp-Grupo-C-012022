package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.TransactionAction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TransactionActionFactory {

    @Autowired
    private lateinit var transactionCancel: TransactionCancel
    @Autowired
    private lateinit var transactionConfirmTransfer: TransactionConfirmTransfer
    @Autowired
    private lateinit var transactionConfirmReception: TransactionConfirmReception

    fun createFromAction(action: TransactionAction): TransactionActionBase {
        return when (action) {
            TransactionAction.CANCEL -> transactionCancel
            TransactionAction.CONFIRM_TRANSFER -> transactionConfirmTransfer
            TransactionAction.CONFIRM_RECEPTION -> transactionConfirmReception
        }
    }
}