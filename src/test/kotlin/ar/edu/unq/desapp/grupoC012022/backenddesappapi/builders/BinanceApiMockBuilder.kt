package ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.apis.BinanceApi
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency
import org.mockito.Mockito.`when`
import kotlin.random.Random

class BinanceApiMockBuilder(private val binanceApiMock: BinanceApi) {
    private val mockedCurrencies = mutableListOf<String>()

    fun mockCurrency(currency: String, price: Double, referenceCurrency: String = "USDT") : BinanceApiMockBuilder {
        `when`(this.binanceApiMock.getCurrency(currency, referenceCurrency)).thenReturn(Currency(Random.nextInt(), "$currency$referenceCurrency", price, true))
        `when`(this.binanceApiMock.isCurrencySupported(currency)).thenReturn(true)
        this.mockedCurrencies.add(currency)
        return this
    }

    fun prepareMock() {
        `when`(this.binanceApiMock.supportedCurrencies()).thenReturn(this.mockedCurrencies)
    }
}