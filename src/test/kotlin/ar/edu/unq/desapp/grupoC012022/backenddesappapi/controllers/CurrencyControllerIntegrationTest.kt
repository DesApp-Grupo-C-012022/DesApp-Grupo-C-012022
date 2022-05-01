package ar.edu.unq.desapp.grupoC012022.backenddesappapi.controllers

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.CurrencyRepository
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.CurrencyService
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDateTime

@SpringBootTest
class CurrencyControllerIntegrationTest {
    @Mock
    private lateinit var currencyServiceMock: CurrencyService
    @Mock
    private lateinit var currencyRepositoryMock: CurrencyRepository
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
            Currency(RandomStringUtils.randomAlphanumeric(15),"BNBUSDT", "1.01", LocalDateTime.now()),
            Currency(RandomStringUtils.randomAlphanumeric(15),"BTCUSDT", "40000.08", LocalDateTime.now()),
        ))
        `when`(this.currencyRepositoryMock.findAll()).thenReturn(mutableListOf())
        `when`(this.currencyRepositoryMock.saveAll(anyIterable())).thenReturn(mutableListOf())
        this.mockMvc.get("/criptos").andExpect {
            status { isOk() }
            content {
                jsonPath("$.[0].symbol") {
                    value("BNBUSDT")
                }
                jsonPath("$.[0].price") {
                    value("1.01")
                }
                jsonPath("$.[1].symbol") {
                    value("BTCUSDT")
                }
                jsonPath("$.[1].price") {
                    value("40000.08")
                }
            }
        }
    }
}
