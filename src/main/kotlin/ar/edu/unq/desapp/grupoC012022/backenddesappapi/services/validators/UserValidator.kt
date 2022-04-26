package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.validators

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.UserRepository
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.InvalidPropertyException
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.UserAlreadyExistsException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserValidator {

    @Autowired
    lateinit var userRepository: UserRepository

    @Throws(UserAlreadyExistsException::class, InvalidPropertyException::class)
    fun validateUser(user: User) {
        user.validate()
        val userFromDb = userRepository.findByEmail(user.email)
        if (userFromDb != null && user.id != userFromDb.id) {
            throw UserAlreadyExistsException()
        }
    }
}