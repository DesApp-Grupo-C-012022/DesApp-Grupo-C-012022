package ar.edu.unq.desapp.grupoC012022.backenddesappapi.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Invalid object property")
class InvalidPropertyException(override val message: String? = ""): Exception(message)