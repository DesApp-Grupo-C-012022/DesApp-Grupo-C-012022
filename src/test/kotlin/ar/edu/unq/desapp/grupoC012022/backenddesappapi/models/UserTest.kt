package ar.edu.unq.desapp.grupoC012022.backenddesappapi.models

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.UserBuilder
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.exceptions.InvalidPropertyException
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserTest {
	private val userBuilder = UserBuilder()

	@Test
	fun userCreation() {
		val user = userBuilder.createUserWithValues().build()
		assertNotNull(user.firstName)
		assertNotNull(user.lastName)
		assertNotNull(user.email)
		assertNotNull(user.homeAddress)
		assertNotNull(user.password)
		assertNotNull(user.mercadoPagoCVU)
		assertNotNull(user.walletAddress)
		assertNotNull(user.reputation)
	}

	@Test
	fun validatingUserWithLessThan3CharacterFirstNameThrowsException() {
		assertThrows<InvalidPropertyException> {
			val user = userBuilder.createUserWithValues().firstName("A").build()
			user.validate()
		}
	}

	@Test
	fun validatingUserWithMoreThan30CharacterFirstNameThrowsException() {
		assertThrows<InvalidPropertyException> {userBuilder.createUserWithValues().firstName("UnNombreLargo45678901234567890A").build().validate()}
	}

	@Test
	fun validatingUserWithMoreThan3AndLessThan30CharacterFirstNameDoesntThrowException() {
		assertDoesNotThrow {userBuilder.createUserWithValues().firstName("UnNombre").build().validate()}
	}

	@Test
	fun validatingUserWithLessThan3CharacterLastNameThrowsException() {
		assertThrows<InvalidPropertyException> {userBuilder.createUserWithValues().lastName("A").build().validate()}
	}

	@Test
	fun validatingUserWithMoreThan30CharacterLastNameThrowsException() {
		assertThrows<InvalidPropertyException> {userBuilder.createUserWithValues().lastName("UnNombreLargo45678901234567890A").build().validate()}
	}

	@Test
	fun validatingUserWithMoreThan3AndLessThan30CharacterLastNameDoesntThrowException() {
		assertDoesNotThrow {userBuilder.createUserWithValues().lastName("UnNombre").build().validate()}
	}

	@Test
	fun validatingUserWithEmptyEmailThrowsException() {
		assertThrows<InvalidPropertyException> {userBuilder.createUserWithValues().email("").build().validate()}
	}

	@Test
	fun validatingUserWithWrongFormatEmailThrowsException() {
		assertThrows<InvalidPropertyException> {userBuilder.createUserWithValues().email("unMail").build().validate()}
	}

	@Test
	fun validatingUserWithCorrectFormatEmailNotThrowsException() {
		assertDoesNotThrow {userBuilder.createUserWithValues().email("unMail@gmail.com").build().validate()}
	}

	@Test
	fun validatingUserWithLessThan10CharacterHomeAddressThrowsException() {
		assertThrows<InvalidPropertyException> {userBuilder.createUserWithValues().homeAddress("A").build().validate()}
	}

	@Test
	fun validatingUserWithMoreThan30CharacterHomeAddressThrowsException() {
		assertThrows<InvalidPropertyException> {userBuilder.createUserWithValues().homeAddress("123456789012345678901234567890A").build().validate()}
	}

	@Test
	fun validatingUserWithEmptyHomeAddressThrowsException() {
		assertThrows<InvalidPropertyException> {userBuilder.createUserWithValues().homeAddress("").build().validate()}
	}

	@Test
	fun validatingUserWithMoreThan10AndLessThan30CharacterHomeAddressDoesntThrowException() {
		assertDoesNotThrow {userBuilder.createUserWithValues().homeAddress("12345678901234567890123456789").build().validate()}
	}

	@Test
	fun validatingUserWithoutHomeAddressThrowsException() {
		assertThrows<InvalidPropertyException> {userBuilder.createUserWithValues().homeAddress("").build().validate()}
	}

	@Test
	fun validatingUserWithWeakPasswordThrowsException() {
		assertThrows<InvalidPropertyException> {userBuilder.createUserWithValues().password("pass").build().validate()}
	}

	@Test
	fun validatingUserWithStrongPasswordNotThrowsException() {
		assertDoesNotThrow {userBuilder.createUserWithValues().password("Super#Strong123Pass").build().validate()}
	}

	@Test
	fun validatingUserWithEmptyPasswordThrowsException() {
		assertThrows<InvalidPropertyException> {userBuilder.createUserWithValues().password("").build().validate()}
	}

	@Test
	fun validatingUserWithMoreThan22CharacterMPCVUThrowsException() {
		assertThrows<InvalidPropertyException> {userBuilder.createUserWithValues().mercadoPagoCVU("123456789012345678901234567890A").build().validate()}
	}

	@Test
	fun validatingUserWithLessThan22CharacterMPCVUThrowsException() {
		assertThrows<InvalidPropertyException> {userBuilder.createUserWithValues().mercadoPagoCVU("A").build().validate()}
	}

	@Test
	fun validatingUserWith22CharacterMPCVUDoesntThrowException() {
		assertDoesNotThrow{userBuilder.createUserWithValues().mercadoPagoCVU("0123456789012345678912").build().validate()}
	}

	@Test
	fun validatingUserWithMoreThan8CharacterAddressWallethrowsException() {
		assertThrows<InvalidPropertyException> {userBuilder.createUserWithValues().walletAddress("123456789").build().validate()}
	}

	@Test
	fun validatingUserWithLessThan8CharacterAddressWalletThrowsException() {
		assertThrows<InvalidPropertyException> {userBuilder.createUserWithValues().walletAddress("A").build().validate()}
	}

	@Test
	fun validatingUserWith8CharacterAddressWalletDoesntThrowException() {
		assertDoesNotThrow{userBuilder.createUserWithValues().walletAddress("12345678").build().validate()}
	}
}
