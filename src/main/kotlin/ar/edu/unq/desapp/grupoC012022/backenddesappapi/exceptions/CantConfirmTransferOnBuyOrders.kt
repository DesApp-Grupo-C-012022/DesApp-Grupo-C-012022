package ar.edu.unq.desapp.grupoC012022.backenddesappapi.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Can not confirm a transfer on a buy order")
class CantConfirmTransferOnBuyOrders (override val message: String? = ""): Exception(message)