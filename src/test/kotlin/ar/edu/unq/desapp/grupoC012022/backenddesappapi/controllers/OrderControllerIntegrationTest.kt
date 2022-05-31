package ar.edu.unq.desapp.grupoC012022.backenddesappapi.controllers

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.OrderBuilder
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.helpers.MockitoHelper
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.OrderService
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.UserNotFoundException
import com.google.gson.Gson
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest
class OrderControllerIntegrationTest {
    @Mock
    private lateinit var orderServiceMock: OrderService
    @InjectMocks
    private lateinit var orderController: OrderController
    private lateinit var mockMvc: MockMvc
    private lateinit var order: OrderBuilder
    private val orderBuilder = OrderBuilder()

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.orderController).build()
        order = orderBuilder
            .createOrderWithValues()
    }

    @Test
    fun postSuccessfulOrderTest() {
        `when`(this.orderServiceMock.create(order.buildDto())).thenReturn(order.id(1).build())
        this.mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/orders")
                    .content(Gson().toJson(order.buildDto()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated)
    }

    @Test
    fun postInvalidUserLastNamePropertyOrderTest() {
        `when`(orderServiceMock.create(MockitoHelper.anyObject())).thenThrow(UserNotFoundException::class.java)
        this.mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/orders")
                    .content(Gson().toJson(order.buildDto()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isUnprocessableEntity)
    }

    @Test
    fun postInvalidUserFirstNamePropertyOrderTest() {
        `when`(orderServiceMock.create(MockitoHelper.anyObject())).thenThrow(UserNotFoundException::class.java)
        this.mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/orders")
                    .content(Gson().toJson(order.buildDto()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isUnprocessableEntity)
    }

    @Test
    fun getActiveOrdersTest() {
        `when`(orderServiceMock.getActives()).thenReturn( listOf(orderBuilder.buildOfferedDto()))

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
                /*jsonPath("$.[0].operationsAmount") {
                    isNumber()
                }*/
                jsonPath("$.[0].reputation") {
                    isNumber()
                }
            }
        }
    }

}
