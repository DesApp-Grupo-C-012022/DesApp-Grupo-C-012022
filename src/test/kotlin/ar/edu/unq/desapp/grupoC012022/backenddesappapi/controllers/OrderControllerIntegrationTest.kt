package ar.edu.unq.desapp.grupoC012022.backenddesappapi.controllers

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.OrderBuilder
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.UserBuilder
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.helpers.DatabaseServiceHelper
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.UserRepository
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.OrderService
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
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderControllerIntegrationTest {

    @Autowired
    private lateinit var orderController: OrderController
    @Autowired
    private lateinit var databaseServiceHelper: DatabaseServiceHelper
    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var orderService: OrderService
    private lateinit var mockMvc: MockMvc
    private lateinit var order: OrderBuilder
    private lateinit var user: UserBuilder
    private lateinit var userFromOrder: User
    private val orderBuilder = OrderBuilder()
    private val userBuilder = UserBuilder()

    @BeforeAll
    fun clearDatabase() = databaseServiceHelper.clearDatabase()

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.orderController).build()
        order = orderBuilder
            .createOrderWithValues()
        user = userBuilder
            .createUserWithValues()
            .firstName("Pepe")
            .lastName("Gonzalez")
        userFromOrder = userRepository.save(userBuilder.build())
    }

    @Test
    @Transactional
    fun postSuccessfulOrderTest() {
        this.mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/orders")
                    .content(Gson().toJson(order.user(userFromOrder).buildDto()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated)
    }

    @Test
    @Transactional
    fun postInvalidUserLastNamePropertyOrderTest() {
        this.mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/orders")
                    .content(Gson().toJson(order.user(userBuilder.lastName("NoEsGonzalez").build()).buildDto()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isUnprocessableEntity)
    }

    @Test
    @Transactional
    fun postInvalidUserFirstNamePropertyOrderTest() {
        this.mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/orders")
                    .content(Gson().toJson(order.user(userBuilder.lastName("NoEsPepe").build()).buildDto()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isUnprocessableEntity)
    }

    @Test
    @Transactional
    fun getActiveOrdersTest() {
        val order = order.user(userFromOrder).build()
        orderService.save(order)
        this.mockMvc.get("/orders").andExpect {
            status { isOk() }
            content {
                jsonPath("$.[0].timestamp") {
                    exists()
                }
                jsonPath("$.[0].ticker") {
                    isString()
                }
                jsonPath("$.[0].quantity") {
                    isNumber()
                }
                jsonPath("$.[0].price") {
                    isNumber()
                }
                jsonPath("$.[0].arsPrice") {
                    isNumber()
                }
                jsonPath("$.[0].userLastname") {
                    isString()
                }
                jsonPath("$.[0].userFirstName") {
                    isString()
                }
                jsonPath("$.[0].operationsAmount") {
                    isNumber()
                }
                jsonPath("$.[0].reputation") {
                    isNumber()
                }
            }
        }
    }

}
