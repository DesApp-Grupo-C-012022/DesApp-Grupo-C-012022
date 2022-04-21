package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.validators

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class UserValidator {

    @Autowired
    lateinit var userRepository: UserRepository

    fun validateUser(user: User) {
        val userFromDb = userRepository.findByEmail(user.email)
        if (userFromDb != null && user.id != userFromDb.id) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "The user already exists")
        }
    }
}