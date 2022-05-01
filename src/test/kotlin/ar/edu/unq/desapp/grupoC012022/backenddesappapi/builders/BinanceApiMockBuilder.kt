package ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.apis.BinanceApi
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.http.ResponseEntity

class BinanceApiMockBuilder(private val binanceApiMock: BinanceApi) {
    private val mockedCurrencies = mutableListOf<String>()

    fun mockCurrency(currency: String, price: String, referenceCurrency: String = "USDT") : BinanceApiMockBuilder {
        val btcResponseEntityMock = mock(ResponseEntity::class.java)
        `when`(btcResponseEntityMock.body).thenReturn("{ \"symbol\" : \"$currency$referenceCurrency\", \"price\" : \"$price\" }")
        `when`(this.binanceApiMock.getCurrency(currency, referenceCurrency)).thenReturn(btcResponseEntityMock)
        `when`(this.binanceApiMock.isCurrencySupported(currency)).thenReturn(true)
        this.mockedCurrencies.add(currency)
        return this
    }

    fun prepareMock() {
        `when`(this.binanceApiMock.supportedCurrencies()).thenReturn(this.mockedCurrencies)
    }
}