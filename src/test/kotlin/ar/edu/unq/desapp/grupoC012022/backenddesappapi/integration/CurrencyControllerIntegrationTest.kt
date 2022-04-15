package ar.edu.unq.desapp.grupoC012022.backenddesappapi.integration

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.controllers.CurrencyController
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CurrencyControllerIntegrationTest {

	@Autowired
	lateinit var currencyController: CurrencyController

	@Test
	fun getCriptosTest() {
		val criptos = this.currencyController.getCriptos()
		assertEquals(2, criptos.size)
	}
}
