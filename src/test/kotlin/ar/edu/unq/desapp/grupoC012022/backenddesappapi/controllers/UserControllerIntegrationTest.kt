package ar.edu.unq.desapp.grupoC012022.backenddesappapi.controllers

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.UserBuilder
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.helpers.DatabaseServiceHelper
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.UserRepository
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
class UserControllerIntegrationTest {
    @Autowired
    private lateinit var userController: UserController
    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var databaseServiceHelper: DatabaseServiceHelper
    private lateinit var mockMvc: MockMvc
    private lateinit var user: UserBuilder

    private val userBuilder = UserBuilder()

    @BeforeAll
    fun clearDatabase() = databaseServiceHelper.clearDatabase()

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
    @Transactional
    fun postSuccessfulUserTest() {
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
    @Transactional
    fun postAlreadyExistentUserTest() {
        userRepository.save(user.build())
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
    @Transactional
    fun postInvalidPropertyUserTest() {
        this.mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/users")
                    .content(Gson().toJson(user.password("a").build()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isUnprocessableEntity)
    }


    @Test
    @Transactional
    fun getUsersTest() {
        val user1 = userBuilder
            .createUserWithValues()
            .firstName("usuario1")
            .email("usuario1@gmail.com")
            .build()
        val user2 = userBuilder
            .createUserWithValues()
            .firstName("usuario2")
            .email("usuario2@gmail.com")
            .build()
        userRepository.save(user1)
        userRepository.save(user2)
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
