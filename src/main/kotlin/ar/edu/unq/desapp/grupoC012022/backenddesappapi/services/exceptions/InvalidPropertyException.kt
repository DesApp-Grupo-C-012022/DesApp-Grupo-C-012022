package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class InvalidPropertyException(errorMessage: String = "Please verify properties") : ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage)