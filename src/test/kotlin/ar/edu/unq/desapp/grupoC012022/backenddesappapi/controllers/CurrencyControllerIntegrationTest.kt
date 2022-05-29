package ar.edu.unq.desapp.grupoC012022.backenddesappapi.controllers

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.CurrencyService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest
class CurrencyControllerIntegrationTest {
    @Mock
    private lateinit var currencyServiceMock: CurrencyService
    @InjectMocks
    private lateinit var currencyController: CurrencyController
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.currencyController).build()
    }

    @Test
    fun getCriptosTest() {
        `when`(this.currencyServiceMock.getCurrencies(null)).thenReturn(listOf(
            Currency("BNBUSDT", 1.01),
            Currency("BTCUSDT", 40000.08),
        ))
        this.mockMvc.get("/currencies").andExpect {
            status { isOk() }
            content {
                jsonPath("$.[0].ticker") {
                    value("BNBUSDT")
                }
                jsonPath("$.[0].usdPrice") {
                    value(1.01)
                }
                jsonPath("$.[1].ticker") {
                    value("BTCUSDT")
                }
                jsonPath("$.[1].usdPrice") {
                    value(40000.08)
                }
            }
        }
    }
}
