package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.aspects.ControllerLoggerAspect
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos.DeserializableUser
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos.LoginUserDto
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos.TokenDto
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.exceptions.*
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.UserRepository
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.security.JwtProvider
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.validators.UserValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var userValidator: UserValidator
    @Autowired
    lateinit var jwtProvider: JwtProvider

    val passwordEncoder = BCryptPasswordEncoder()


    @Throws(UserAlreadyExistsException::class, InvalidPropertyException::class)
    @Transactional
    fun save(user: User): User {
        userValidator.validateUser(user)
        user.password = passwordEncoder.encode(user.password)
        return userRepository.save(user)
    }

    fun getUsers(): List<DeserializableUser?> {
        return userRepository.findAll().map { it.toDeserializableUser() }
    }

    @Throws(UserNotFoundException::class)
    fun findById(id: Int): User {
        return userRepository.findById(id).orElseThrow { UserNotFoundException() }
    }

    @Throws(UserNotFoundException::class)
    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    @Throws(UserNotFoundException::class, InvalidPropertyException::class)
    fun getByName(firstName: String?, lastname: String?): User {
        if(firstName.isNullOrEmpty() || lastname.isNullOrEmpty())
            throw InvalidPropertyException()

        return userRepository.findByFirstNameAndLastName(firstName, lastname) ?: throw UserNotFoundException()
    }

    fun login(userDto: LoginUserDto): TokenDto {
        val user = findByEmail(userDto.email)
        if (user == null || !passwordEncoder.matches(userDto.password, user.password)) {
            throw InvalidUserOrPasswordException()
        }
        ControllerLoggerAspect.user = user
        return TokenDto(jwtProvider.createToken(user))
    }

    fun validate(token: String): TokenDto {
        if(!jwtProvider.validate(token))
            throw InvalidOrMissingTokenException()

        val email = jwtProvider.getEmailFromToken(token)
        if (findByEmail(email) == null)
            throw InvalidOrMissingTokenException()

        return TokenDto(token)
    }
}