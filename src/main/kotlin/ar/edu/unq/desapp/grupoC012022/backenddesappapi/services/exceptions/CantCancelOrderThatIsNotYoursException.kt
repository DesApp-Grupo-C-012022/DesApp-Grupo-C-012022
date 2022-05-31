package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Can not cancel an order that is not yours")
class CantCancelOrderThatIsNotYoursException(override val message: String? = ""): Exception(message)