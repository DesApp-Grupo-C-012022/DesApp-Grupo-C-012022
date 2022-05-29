package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.TransactionAction
import org.springframework.stereotype.Component

@Component
class TransactionActionFactory {
    fun createFromAction(action: TransactionAction): ITransactionAction {
        return when (action) {
            TransactionAction.CANCEL -> TransactionCancel()
            TransactionAction.CONFIRM_TRANSFER -> TransactionConfirmTransfer()
            TransactionAction.CONFIRM_RECEPTION -> TransactionConfirmReception()
        }
    }
}