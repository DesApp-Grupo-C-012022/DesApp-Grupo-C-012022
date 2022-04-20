package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    fun save(user: User): User {
        // TODO: Replace this validation with a custom user validator
        if (userRepository.findByEmail(user.email) != null) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "The user already exists")
        }
        return userRepository.save(user)
    }
}