package ar.edu.unq.desapp.grupoC012022.backenddesappapi.controllers

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.UserBuilder
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.helpers.MockitoHelper
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.UserService
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.InvalidPropertyException
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.UserAlreadyExistsException
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
class UserControllerIntegrationTest {
    @Mock
    private lateinit var userServiceMock: UserService
    @InjectMocks
    private lateinit var userController: UserController
    private lateinit var mockMvc: MockMvc
    private lateinit var user: UserBuilder
    private val userBuilder = UserBuilder()

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.userController).build()
        user = userBuilder
            .createUserWithValues()
            .firstName("Pepe")
            .lastName("Rodriguez")
            .email("someawesomeemail@testing.com")
    }

    @Test
    fun postSuccessfulUserTest() {
        `when`(userServiceMock.save(user.build())).thenReturn(user.id(1).build())
        this.mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/users")
                    .content(Gson().toJson(user.build()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated)
    }

    @Test
    fun postAlreadyExistentUserTest() {
        `when`(userServiceMock.save(MockitoHelper.anyObject())).thenThrow(UserAlreadyExistsException::class.java)
        this.mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/users")
                    .content(Gson().toJson(user.build()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isForbidden)
    }

    @Test
    fun postInvalidPropertyUserTest() {
        `when`(userServiceMock.save(MockitoHelper.anyObject())).thenThrow(InvalidPropertyException::class.java)
        this.mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/users")
                    .content(Gson().toJson(user.build()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isUnprocessableEntity)
    }


    @Test
    fun getUsersTest() {
        `when`(this.userServiceMock.getUsers()).thenReturn(listOf(
            userBuilder.createUserWithValues().firstName("usuario1").build().toDeserializableUser(),
            userBuilder.createUserWithValues().firstName("usuario2").build().toDeserializableUser(),
        ))
        this.mockMvc.get("/users").andExpect {
            status { isOk() }
            content {
                jsonPath("$.[0].firstName") {
                    value("usuario1")
                }
                jsonPath("$.[1].firstName") {
                    value("usuario2")
                }
            }
        }
    }
}
