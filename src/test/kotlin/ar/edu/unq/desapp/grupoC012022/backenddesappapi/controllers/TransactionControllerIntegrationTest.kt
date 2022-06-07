package ar.edu.unq.desapp.grupoC012022.backenddesappapi.controllers

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.OrderBuilder
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.UserBuilder
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos.TransactionDto
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.helpers.DatabaseServiceHelper
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Order
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.TransactionAction
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.OrderService
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.UserService
import com.google.gson.Gson
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionControllerIntegrationTest {


    @Autowired
    private lateinit var transactionController: TransactionController
    @Autowired
    private lateinit var databaseServiceHelper: DatabaseServiceHelper
    @Autowired
    private lateinit var userService: UserService
    @Autowired
    private lateinit var orderService: OrderService
    private lateinit var mockMvc: MockMvc
    private lateinit var order: OrderBuilder
    private lateinit var user: UserBuilder
    private lateinit var userFromOrder: User
    private lateinit var orderFromDb: Order
    private val orderBuilder = OrderBuilder()
    private val userBuilder = UserBuilder()

    @BeforeAll
    fun clearDatabase() = databaseServiceHelper.clearDatabase()

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.transactionController).build()
        user = userBuilder
            .createUserWithValues()
            .firstName("Pepe")
            .lastName("Gonzalez")
        userFromOrder = userService.save(userBuilder.build())
        order = orderBuilder
            .createOrderWithValues()
        orderFromDb = orderService.save(order.user(userFromOrder).build())
    }

    @Test
    @Transactional
    fun postUserCannotBuyItsOwnOrderTransactionTest() {
        val userFromDb1 = userService.getByName(userFromOrder.firstName, userFromOrder.lastName)
        assert(userFromDb1.operationsAmount == 100)
        val transactionDto = TransactionDto(1, 1, TransactionAction.CONFIRM_TRANSFER)
        this.mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/transactions")
                    .content(Gson().toJson(transactionDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    @Transactional
    fun postSuccessfulTransactionTest() {
        var anotherUser = user
            .createUserWithValues()
            .firstName("Roberto")
            .lastName("Gomez")
            .email("robertogomez@gmail.com")
            .build()
        anotherUser = userService.save(anotherUser)
        assert(anotherUser.operationsAmount == 100)
        val userFromDb = userService.getByName(userFromOrder.firstName, userFromOrder.lastName)
        assert(userFromDb.operationsAmount == 100)
        val transactionDto = TransactionDto(anotherUser.id!!, 1, TransactionAction.CONFIRM_RECEPTION)
        this.mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/transactions")
                    .content(Gson().toJson(transactionDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(MockMvcResultMatchers.status().isCreated)
        anotherUser = userService.findById(anotherUser.id!!)
        assert(anotherUser.operationsAmount == 101)
    }
}
