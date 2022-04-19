package ar.edu.unq.desapp.grupoC012022.backenddesappapi.integration

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.controllers.CurrencyController
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.helpers.ExternalApiHelper
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.CurrencyService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CurrencyControllerIntegrationTest {

    @Test
    fun getCriptosTest() {
        val binanceApiMock = ExternalApiHelper.getBinanceApiMock()
        val currencyServiceMock = mock(
            CurrencyService::class.java,
            Mockito.withSettings().useConstructor(binanceApiMock).defaultAnswer(Mockito.CALLS_REAL_METHODS)
        )
        `when`(currencyServiceMock.supportedCurrencies()).thenReturn(listOf("BNB", "BTC"))
        val currencyController = CurrencyController(currencyServiceMock)
        val criptos = currencyController.getCriptos(null)
        assertEquals(2, criptos.size)
    }
}
