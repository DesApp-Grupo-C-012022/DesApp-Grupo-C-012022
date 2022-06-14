package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.apis.BinanceApi
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.BinanceApiMockBuilder
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.CurrencyBuilder
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.helpers.MockitoHelper
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.CurrencyRepository
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.CurrencyNotSupportedException
import org.junit.jupiter.api.Assertions.*
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
	private val currencyBuilder = CurrencyBuilder()

	@BeforeEach
	fun setUp() {
		MockitoAnnotations.openMocks(this)
		val binanceApiMockBuilder = BinanceApiMockBuilder(this.binanceApiMock)
		binanceApiMockBuilder
			.mockCurrency("BNB", 1.01)
			.mockCurrency("BTC", 40000.1254)
			.prepareMock()
		val bnbCurrency = currencyBuilder.createCurrencyWithValues().ticker("BNBUSDT").usdPrice(1.01).build()
		val btcCurrency = currencyBuilder.createCurrencyWithValues().ticker("BTCUSDT").usdPrice(40000.1254).build()
		`when`(currencyRepositoryMock.findByTimestampGreaterThan(MockitoHelper.anyObject())).thenReturn(
			listOf(bnbCurrency, btcCurrency)
		)
		`when`(currencyRepositoryMock.findByTickerAndLatest("BNB")).thenReturn(bnbCurrency)
		`when`(currencyRepositoryMock.findByTickerAndLatest("BTC")).thenReturn(btcCurrency)
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
	fun getPricesTest() {
		val criptos = this.subject.getPrices()
		assertEquals(2, criptos.size)
		assertEquals("BNBUSDT", criptos.first().ticker)
		assertNotNull(criptos.first().timestamp)
		assertEquals("BTCUSDT", criptos.last().ticker)
		assertNotNull(criptos.last().timestamp)
	}
}
