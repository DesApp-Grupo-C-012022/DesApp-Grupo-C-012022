package ar.edu.unq.desapp.grupoC012022.backenddesappapi.helpers

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.apis.BinanceApi
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.http.ResponseEntity

class ExternalApiHelper {
    companion object {
        private val binanceApiMock = mock(BinanceApi::class.java)

        fun getBinanceApiMock(): BinanceApi {
            mockCurrency("BNB", "1.01")
            mockCurrency("BTC", "40000.1254")
            return binanceApiMock
        }

        private fun mockCurrency(currency: String, price: String, referenceCurrency: String = "USDT") {
            val btcResponseEntityMock = mock(ResponseEntity::class.java)
            `when`(btcResponseEntityMock.body).thenReturn("{ \"symbol\" : \"$currency$referenceCurrency\", \"price\" : \"$price\" }")
            `when`(binanceApiMock.getCurrency(currency, referenceCurrency))
                .thenReturn(btcResponseEntityMock)
        }
    }
}