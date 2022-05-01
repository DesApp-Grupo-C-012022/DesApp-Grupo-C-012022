package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.apis.BinanceApi
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.BinanceApiMockBuilder
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.CurrencyRepository
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.CurrencyNotSupportedException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CurrencyServiceTest {

	@Mock
	private lateinit var binanceApiMock : BinanceApi
	@Mock
	private lateinit var currencyRepositoryMock: CurrencyRepository
	@InjectMocks
	private lateinit var subject : CurrencyService

	@BeforeEach
	fun setUp() {
		MockitoAnnotations.openMocks(this)
		val binanceApiMockBuilder = BinanceApiMockBuilder(this.binanceApiMock)
		binanceApiMockBuilder
			.mockCurrency("BNB", "1.01")
			.mockCurrency("BTC", "40000.1254")
			.prepareMock()
		Mockito.`when`(this.currencyRepositoryMock.findAll()).thenReturn(mutableListOf())
		Mockito.`when`(this.currencyRepositoryMock.saveAll(Mockito.anyIterable())).thenReturn(mutableListOf())
	}

	@Test
	fun getCriptosTest() {
		val criptos = this.subject.getCurrencies("USDT")
		assertEquals(2, criptos.size)
	}

	@Test
	fun getCriptoTest() {
		val cripto = this.subject.getCurrency("BNB","USDT")
		assertEquals("BNBUSDT", cripto.ticker)
		assertEquals("1.01", cripto.usdPrice)
	}

	@Test
	fun getCriptoNotSupportedTest() {
		assertThrows<CurrencyNotSupportedException> {
			this.subject.getCurrency("SOMECRIPTO","USDT")
		}
	}
}
