package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.TransactionAction

class TransactionActionFactory {
    companion object {
        fun createFromAction(action: TransactionAction): ITransactionAction {
            return when (action) {
                TransactionAction.CANCEL -> TransactionCancel()
                TransactionAction.CONFIRM_TRANSFER -> TransactionConfirmTransfer()
                TransactionAction.CONFIRM_RECEPTION -> TransactionConfirmReception()
            }
        }
    }
}