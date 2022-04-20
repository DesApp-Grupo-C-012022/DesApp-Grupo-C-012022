package ar.edu.unq.desapp.grupoC012022.backenddesappapi.unit

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.apis.BinanceApi
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.CurrencyNotSupportedException
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.helpers.ExternalApiHelper
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.CurrencyService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*

class CurrencyServiceTest {
	private var binanceApiMock: BinanceApi = ExternalApiHelper.getBinanceApiMock()
	private var subject: CurrencyService = CurrencyService(binanceApiMock)

	@Test
	fun getCriptosTest() {
		this.subject = mock(CurrencyService::class.java, withSettings().useConstructor(binanceApiMock).defaultAnswer(CALLS_REAL_METHODS))
		`when`(this.subject.supportedCurrencies()).thenReturn(listOf("BNB", "BTC"))
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
