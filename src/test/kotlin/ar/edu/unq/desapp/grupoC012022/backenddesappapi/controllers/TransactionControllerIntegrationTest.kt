package ar.edu.unq.desapp.grupoC012022.backenddesappapi.controllers

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos.TransactionDto
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.TransactionAction
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction.TransactionService
import com.google.gson.Gson
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest
class TransactionControllerIntegrationTest {

    @Autowired @Mock
    private lateinit var transactionService: TransactionService
    @InjectMocks
    private lateinit var transactionController: TransactionController
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.transactionController).build()
    }

    @Test
    fun postSuccessfulTransactionTest() {
        val transactionDto = TransactionDto(1, 1, TransactionAction.CONFIRM_TRANSFER)
        `when`(transactionService.processTransaction(transactionDto)).thenAnswer {}
        this.mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/transactions")
                    .content(Gson().toJson(transactionDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(MockMvcResultMatchers.status().isCreated)
    }
}
