package ar.edu.unq.desapp.grupoC012022.backenddesappapi.models

enum class Operation {
    BUY, SELL;
    companion object {
        fun opposite(op: Operation): Operation {
            return if (op == BUY) SELL else BUY
        }

        fun toTransactionAction(op: Operation): TransactionAction {
            return if (op == BUY) TransactionAction.CONFIRM_RECEPTION else TransactionAction.CONFIRM_TRANSFER
        }
    }
}
