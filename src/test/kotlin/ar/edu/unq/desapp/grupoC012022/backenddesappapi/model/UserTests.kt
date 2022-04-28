package ar.edu.unq.desapp.grupoC012022.backenddesappapi.model

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.UserBuilder
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserTests {
	private val userBuilder = UserBuilder()

	@Test
	fun userCreation() {
		val user = userBuilder.createUserWithValues().build()
		assertNotNull(user.FirstName)
		assertNotNull(user.LastName)
		assertNotNull(user.Email)
		assertNotNull(user.HomeAddress)
		assertNotNull(user.Password)
		assertNotNull(user.MercadoPagoCVU)
		assertNotNull(user.WalletAddress)
		assertNotNull(user.Reputation)
	}

	@Test
	fun userCreationWithLessThan3CharacterFirstNameThrowsException() {
		assertThrows<IllegalArgumentException> {userBuilder.createUserWithValues().firstName("A").build()}
	}

	@Test
	fun userCreationWithMoreThan30CharacterFirstNameThrowsException() {
		assertThrows<IllegalArgumentException> {userBuilder.createUserWithValues().firstName("UnNombreLargo45678901234567890A").build()}
	}

	@Test
	fun userCreationWithMoreThan3AndLessThan30CharacterFirstNameDoesntThrowException() {
		assertDoesNotThrow {userBuilder.createUserWithValues().firstName("UnNombre").build()}
	}

	@Test
	fun userCreationWithLessThan3CharacterLastNameThrowsException() {
		assertThrows<IllegalArgumentException> {userBuilder.createUserWithValues().lastName("A").build()}
	}

	@Test
	fun userCreationWithMoreThan30CharacterLastNameThrowsException() {
		assertThrows<IllegalArgumentException> {userBuilder.createUserWithValues().lastName("UnNombreLargo45678901234567890A").build()}
	}

	@Test
	fun userCreationWithMoreThan3AndLessThan30CharacterLastNameDoesntThrowException() {
		assertDoesNotThrow {userBuilder.createUserWithValues().lastName("UnNombre").build()}
	}

	@Test
	fun userCreationWithEmptyEmailThrowsException() {
		assertThrows<IllegalArgumentException> {userBuilder.createUserWithValues().email("").build()}
	}

	@Test
	fun userCreationWithWrongFormatEmailThrowsException() {
		assertThrows<IllegalArgumentException> {userBuilder.createUserWithValues().email("unMail").build()}
	}

	@Test
	fun userCreationWithCorrectFormatEmailNotThrowsException() {
		assertDoesNotThrow {userBuilder.createUserWithValues().email("unMail@gmail.com").build()}
	}

	@Test
	fun userCreationWithLessThan10CharacterHomeAddressThrowsException() {
		assertThrows<IllegalArgumentException> {userBuilder.createUserWithValues().homeAddress("A").build()}
	}

	@Test
	fun userCreationWithMoreThan30CharacterHomeAddressThrowsException() {
		assertThrows<IllegalArgumentException> {userBuilder.createUserWithValues().homeAddress("123456789012345678901234567890A").build()}
	}

	@Test
	fun userCreationWithEmptyHomeAddressThrowsException() {
		assertThrows<IllegalArgumentException> {userBuilder.createUserWithValues().homeAddress("").build()}
	}

	@Test
	fun userCreationWithMoreThan10AndLessThan30CharacterHomeAddressDoesntThrowException() {
		assertDoesNotThrow {userBuilder.createUserWithValues().homeAddress("12345678901234567890123456789").build()}
	}

	@Test
	fun userCreationWithoutHomeAddressThrowsException() {
		assertThrows<IllegalArgumentException> {userBuilder.createUserWithValues().homeAddress("").build()}
	}

	@Test
	fun userCreationWithWeakPasswordThrowsException() {
		assertThrows<IllegalArgumentException> {userBuilder.createUserWithValues().password("pass").build()}
	}

	@Test
	fun userCreationWithStrongPasswordNotThrowsException() {
		assertDoesNotThrow {userBuilder.createUserWithValues().password("Super#Strong123Pass").build()}
	}

	@Test
	fun userCreationWithEmptyPasswordThrowsException() {
		assertThrows<IllegalArgumentException> {userBuilder.createUserWithValues().password("").build()}
	}

	@Test
	fun userCreationWithMoreThan22CharacterMPCVUThrowsException() {
		assertThrows<IllegalArgumentException> {userBuilder.createUserWithValues().mercadoPagoCVU("123456789012345678901234567890A").build()}
	}

	@Test
	fun userCreationWithLessThan22CharacterMPCVUThrowsException() {
		assertThrows<IllegalArgumentException> {userBuilder.createUserWithValues().mercadoPagoCVU("A").build()}
	}

	@Test
	fun userCreationWith22CharacterMPCVUDoesntThrowException() {
		assertDoesNotThrow{userBuilder.createUserWithValues().mercadoPagoCVU("0123456789012345678912").build()}
	}

	@Test
	fun userCreationWithMoreThan8CharacterAddressWallethrowsException() {
		assertThrows<IllegalArgumentException> {userBuilder.createUserWithValues().walletAddress("123456789").build()}
	}

	@Test
	fun userCreationWithLessThan8CharacterAddressWalletThrowsException() {
		assertThrows<IllegalArgumentException> {userBuilder.createUserWithValues().walletAddress("A").build()}
	}

	@Test
	fun userCreationWith8CharacterAddressWalletDoesntThrowException() {
		assertDoesNotThrow{userBuilder.createUserWithValues().walletAddress("12345678").build()}
	}
	
}
