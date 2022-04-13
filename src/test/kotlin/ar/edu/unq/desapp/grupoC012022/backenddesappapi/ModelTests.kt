package ar.edu.unq.desapp.grupoC012022.backenddesappapi

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ModelTests {

	private fun aCurrency(): Currency {
		return Currency("USDT", "Tether")
	}

	private fun aUser(): User {
		return User("Name", "LastName", "email@email.com", "Addres 123", "Super#Strong123Pass", "MercadoPagoCVU12345678", "Address1",100 )
	}

	private fun aUserWithFirstName(FirstNAme:String): User {
		return User(FirstNAme, "LastName", "email@email.com", "Addres 123", "Super#Strong123Pass", "MercadoPagoCVU12345678", "Address1",100 )
	}

	private fun aUserWithLastName(LastName: String): User {
		return User("FirstNAme", LastName, "email@email.com", "Addres 123", "Super#Strong123Pass", "MercadoPagoCVU12345678", "Address1",100 )
	}

	private fun aUserWithEmail(Email: String): User {
		return User("FirstNAme", "LastName", Email, "Addres 123","Super#Strong123Pass", "MercadoPagoCVU12345678", "Address1",100 )
	}

	private fun aUserWithHomeAddres(HomeAddres: String): User {
		return User("FirstNAme", "LastName", "email@email.com", HomeAddres, "Super#Strong123Pass", "MercadoPagoCVU12345678", "Address1",100 )
	}

	private fun aUserWithPassword(Password: String): User {
		return User("FirstNAme", "LastName", "email@email.com", "Addres 123", Password, "MercadoPagoCVU12345678", "Address1",100 )
	}

	private fun aUserWithMPCVU(MPCVU: String): User {
		return User("FirstNAme", "LastName", "email@email.com", "Addres 123", "Super#Strong123Pass", MPCVU, "Address1",100 )
	}

	private fun aUserWithWalletAddress(MPCVU: String): User {
		return User("FirstNAme", "LastName", "email@email.com", "Addres 123", "Super#Strong123Pass", MPCVU, "Address1",100 )
	}

	@Test
	fun currencyCreation() {
		val currency = aCurrency()
		assertNotNull(currency.name)
		assertNotNull(currency.ticker)
	}

	@Test
	fun userCreation() {
		val user = aUser()
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
		assertThrows<IllegalArgumentException> {aUserWithFirstName("A")}
	}

	@Test
	fun userCreationWithMoreThan30CharacterFirstNameThrowsException() {
		assertThrows<IllegalArgumentException> {aUserWithFirstName("123456789012345678901234567890A")}
	}

	@Test
	fun userCreationWithLessThan3CharacterLastNameThrowsException() {
		assertThrows<IllegalArgumentException> {aUserWithLastName("A")}
	}

	@Test
	fun userCreationWithMoreThan30CharacterLastNameThrowsException() {
		assertThrows<IllegalArgumentException> {aUserWithLastName("123456789012345678901234567890A")}
	}

	@Test
	fun userCreationWithoutEmailThrowsException() {
		assertThrows<IllegalArgumentException> {aUserWithEmail("")}
	}

	@Test
	fun userCreationWithWrongFormatEmailThrowsException() {
		assertThrows<IllegalArgumentException> {aUserWithEmail("unMail")}
	}

	@Test
	fun userCreationWithCorrectFormatEmailNotThrowsException() {
		assertDoesNotThrow {aUserWithEmail("unMail@gmail.com")}
	}

	@Test
	fun userCreationWithLessThan10CharacterHomeAddressThrowsException() {
		assertThrows<IllegalArgumentException> {aUserWithHomeAddres("A")}
	}

	@Test
	fun userCreationWithMoreThan30CharacterHomeAddressThrowsException() {
		assertThrows<IllegalArgumentException> {aUserWithHomeAddres("123456789012345678901234567890A")}
	}

	@Test
	fun userCreationWithoutHomeAddressThrowsException() {
		assertThrows<IllegalArgumentException> {aUserWithHomeAddres("")}
	}

	@Test
	fun userCreationWithWeakPasswordThrowsException() {
		assertThrows<IllegalArgumentException> {aUserWithPassword("pass")}
	}

	@Test
	fun userCreationWithStrongPasswordNotThrowsException() {
		assertDoesNotThrow {aUserWithPassword("Super#Strong123Pass")}
	}

	@Test
	fun userCreationWithMoreThan22CharacterMPCVUThrowsException() {
		assertThrows<IllegalArgumentException> {aUserWithMPCVU("123456789012345678901234567890A")}
	}

	@Test
	fun userCreationWithLessThan22CharacterMPCVUThrowsException() {
		assertThrows<IllegalArgumentException> {aUserWithMPCVU("A")}
	}

	@Test
	fun userCreationWithMoreThan8CharacterAddressWallethrowsException() {
		assertThrows<IllegalArgumentException> {aUserWithWalletAddress("123456789")}
	}

	@Test
	fun userCreationWithLessThan8CharacterAddressWalletThrowsException() {
		assertThrows<IllegalArgumentException> {aUserWithWalletAddress("A")}
	}
	
}
