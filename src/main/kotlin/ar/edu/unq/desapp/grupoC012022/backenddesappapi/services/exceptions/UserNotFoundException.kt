package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "User not found")
class UserNotFoundException(override val message: String? = ""): Exception(message)