package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.apis.BinanceApi
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.BinanceApiMockBuilder
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.CurrencyBuilder
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.jobs.CurrencyPriceJob
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.CurrencyRepository
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.CurrencyNotSupportedException
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CurrencyServiceTest {

	@Mock
	private lateinit var binanceApiMock : BinanceApi
	@Mock
	private lateinit var currencyRepositoryMock : CurrencyRepository

	@InjectMocks
	private lateinit var subject : CurrencyService
	private val currencyBuilder = CurrencyBuilder().createCurrencyWithValues()

	@BeforeEach
	fun setUp() {
		MockitoAnnotations.openMocks(this)
		val binanceApiMockBuilder = BinanceApiMockBuilder(this.binanceApiMock)
		binanceApiMockBuilder
			.mockCurrency("BNB", 1.01)
			.mockCurrency("BTC", 40000.1254)
			.prepareMock()

		`when`(currencyRepositoryMock.findByTickerAndLatest("BNB",true)).thenReturn(currencyBuilder.ticker("BNBUSDT").usdPrice(1.01).build())
	}

	@Test
	fun getCurrenciesTest() {
		val criptos = this.subject.getCurrencies()
		assertEquals(2, criptos.size)
	}

	@Test
	fun getCurrencyTest() {
		val cripto = this.subject.getCurrency("BNB")
		assertEquals("BNBUSDT", cripto?.ticker)
		assertEquals(1.01, cripto?.usdPrice)
	}

	@Test
	fun updateCurrencyTest() {
		val cripto = this.subject.updateCurrency("BNB")
		assertEquals("BNBUSDT", cripto.ticker)
		assertEquals(1.01, cripto.usdPrice)
	}

	@Test
	fun updateCurrencyNotSupportedTest() {
		assertThrows<CurrencyNotSupportedException> {
			this.subject.updateCurrency("SOMECRIPTO")
		}
	}

	@Test
	fun currencyPriceJobRunTest() {
		assertDoesNotThrow { CurrencyPriceJob().run() }
	}
}
