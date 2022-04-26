package ar.edu.unq.desapp.grupoC012022.backenddesappapi.model

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.PriceBuilder
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PriceTest {
	private val priceBuilder = PriceBuilder()

	@Test
	fun priceCreation() {
		val price = priceBuilder.createPriceWithValues().build()
		assertNotNull(price.bidCurrency)
		assertNotNull(price.sellingPrice)
		assertNotNull(price.askCurrency)
		assertNotNull(price.timestamp)
	}

	@Test
	fun priceCreationWithNegativeSellingPriceThrowsException() {
		assertThrows<IllegalArgumentException> {priceBuilder.createPriceWithValues().sellingPrice(-5).build()}
	}
}
