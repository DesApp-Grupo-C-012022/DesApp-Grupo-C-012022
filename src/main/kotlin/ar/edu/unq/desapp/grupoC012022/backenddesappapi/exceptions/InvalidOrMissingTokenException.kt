package ar.edu.unq.desapp.grupoC012022.backenddesappapi.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Invalid or missing token")
class InvalidOrMissingTokenException(override val message: String? = "Invalid or missing token"): Exception(message)