package ar.edu.unq.desapp.grupoC012022.backenddesappapi.models

import kotlin.math.abs

enum class Operation {
    BUY, SELL;
    companion object {
        fun opposite(op: Operation): Operation {
            return values()[abs(op.ordinal - 1)]
        }
    }
}
