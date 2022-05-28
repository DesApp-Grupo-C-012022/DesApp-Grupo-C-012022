package ar.edu.unq.desapp.grupoC012022.backenddesappapi.controllers

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest
class DocumentationControllerIntegrationTest {

    @Autowired
    private lateinit var documentationController: DocumentationController
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.documentationController).build()
    }
    @Test
    fun getDocumentationTest() {
        this.mockMvc.get("/swagger").andExpect {
            status { isPermanentRedirect() }
        }
    }
}
