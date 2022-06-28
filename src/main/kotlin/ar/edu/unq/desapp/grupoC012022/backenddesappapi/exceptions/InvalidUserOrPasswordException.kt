package ar.edu.unq.desapp.grupoC012022.backenddesappapi.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Invalid user or password")
class InvalidUserOrPasswordException(override val message: String? = ""): Exception(message)