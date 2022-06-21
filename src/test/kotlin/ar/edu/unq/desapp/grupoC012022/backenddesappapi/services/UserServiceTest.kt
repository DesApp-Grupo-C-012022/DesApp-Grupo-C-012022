package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.UserBuilder
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.UserRepository
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.exceptions.InvalidPropertyException
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.exceptions.UserAlreadyExistsException
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.exceptions.UserNotFoundException
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.validators.UserValidator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class UserServiceTest {

	@Mock
	private lateinit var userValidatorMock: UserValidator

	@Mock
	private lateinit var userRepositoryMock: UserRepository

	@InjectMocks
	private lateinit var subject : UserService
	private lateinit var user: UserBuilder
	private val userBuilder = UserBuilder()

	@BeforeEach
	fun setUp() {
		MockitoAnnotations.openMocks(this)
		user = userBuilder
			.createUserWithValues()
			.firstName("Pepe")
			.lastName("Rodriguez")
			.email("someawesomeemail@testing.com")

	}

	@Test
	fun saveUserTest() {
		val userToSave = user.build()
		val dbuser = user.id(1).build()
		`when`(userRepositoryMock.save(userToSave)).thenReturn(dbuser)
		subject.save(userToSave)
		verify(userRepositoryMock, times(1)).save(userToSave)
	}

	@Test
	fun saveAlreadyExistentUserTest() {
		val userToSave = user.build()
		`when`(userValidatorMock.validateUser(userToSave)).thenThrow(UserAlreadyExistsException::class.java)
		assertThrows<UserAlreadyExistsException> { subject.save(userToSave) }
		verify(userRepositoryMock, times(0)).save(userToSave)
	}

	@Test
	fun saveInvalidPropertyUserTest() {
		val userToSave = user.build()
		`when`(userValidatorMock.validateUser(userToSave)).thenThrow(InvalidPropertyException::class.java)
		assertThrows<InvalidPropertyException> { subject.save(userToSave) }
		verify(userRepositoryMock, times(0)).save(userToSave)
	}

	@Test
	fun getAllUserTest() {
		val dbuser = user.id(1).build()
		`when`(userRepositoryMock.findAll()).thenReturn(listOf(dbuser))
		assert(subject.getUsers().get(0)!!.id == listOf(dbuser.toDeserializableUser()).get(0).id)
	}

	@Test
	fun getByNameTest() {
		val dbuser = user.id(1).build()
		`when`(userRepositoryMock.findByFirstNameAndLastName(dbuser.firstName,dbuser.lastName)).thenReturn(dbuser)
		assert(subject.getByName(dbuser.firstName,dbuser.lastName) == dbuser)
	}

	@Test
	fun getByInvalidNameUserTest() {
		val dbuser = user.id(1).build()
		`when`(userRepositoryMock.findByFirstNameAndLastName(dbuser.firstName,dbuser.lastName)).thenReturn(null)
		assertThrows<UserNotFoundException> { subject.getByName(dbuser.firstName,dbuser.lastName) }
	}

	@Test
	fun getByNameWithNullFirtNameUserTest() {
		val dbuser = user.id(1).build()
		`when`(userRepositoryMock.findByFirstNameAndLastName(dbuser.firstName,dbuser.lastName)).thenReturn(dbuser)
		assertThrows<InvalidPropertyException> { subject.getByName(null,dbuser.lastName) }
	}

	@Test
	fun getByNameWithNullLastNameUserTest() {
		val dbuser = user.id(1).build()
		`when`(userRepositoryMock.findByFirstNameAndLastName(dbuser.firstName,dbuser.lastName)).thenReturn(dbuser)
		assertThrows<InvalidPropertyException> { subject.getByName(dbuser.firstName,null) }
	}
}
