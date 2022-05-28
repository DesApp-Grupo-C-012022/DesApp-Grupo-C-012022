package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.UserRepository
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.InvalidPropertyException
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.UserAlreadyExistsException
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.UserNotFoundException
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.validators.UserValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var userValidator: UserValidator

    @Throws(UserAlreadyExistsException::class, InvalidPropertyException::class)
    fun save(user: User): User {
        userValidator.validateUser(user)
        return userRepository.save(user)
    }

    fun getUsers(): List<User?> {
        return userRepository.findAll()
    }

    @Throws(UserNotFoundException::class, InvalidPropertyException::class)
    fun getByName(firstName: String?, lastname: String?): User {
        if(firstName.isNullOrEmpty() || lastname.isNullOrEmpty())
            throw InvalidPropertyException()

        return userRepository.findByFirstNameAndLastName(firstName, lastname) ?: throw UserNotFoundException()
    }
}