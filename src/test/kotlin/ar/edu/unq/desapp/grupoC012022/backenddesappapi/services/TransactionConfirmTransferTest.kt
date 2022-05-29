package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.OrderBuilder
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.PriceBuilder
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.UserBuilder
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Order
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction.TransactionConfirmTransfer
import org.joda.time.LocalDateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TransactionConfirmTransferTest {

    @Mock
    private lateinit var userService: UserService
    @Autowired @InjectMocks
    private lateinit var subject: TransactionConfirmTransfer

    private lateinit var userFromOrder: User
    private lateinit var executingUser: User
    private lateinit var order: Order

    private val orderBuilder = OrderBuilder()
    private val userBuilder = UserBuilder()
    private val priceBuilder = PriceBuilder()

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        userFromOrder = userBuilder.createUserWithValues().id(1).reputation(10).build()
        executingUser = userBuilder.createUserWithValues().id(2).reputation(10).build()
        `when`(userService.save(userFromOrder)).thenReturn(userFromOrder)
        `when`(userService.save(executingUser)).thenReturn(executingUser)
    }

    @Test
    fun tbd() {
        order = orderBuilder
            .createOrderWithValues()
            .user(userFromOrder)
            .price(
                priceBuilder
                    .createPriceWithValues()
                    .timestamp(
                        LocalDateTime.now().minusMinutes(20)
                    ).build()
            )
            .build()
        subject.process(order, executingUser)
        assert(userFromOrder.reputation == 20)
        assert(executingUser.reputation == 20)
    }

    @Test
    fun tbd2() {
        order = orderBuilder
            .createOrderWithValues()
            .user(userFromOrder)
            .price(
                priceBuilder
                    .createPriceWithValues()
                    .timestamp(
                        LocalDateTime.now().minusMinutes(35)
                    ).build()
            )
            .build()
        subject.process(order, executingUser)
        assert(userFromOrder.reputation == 15)
        assert(executingUser.reputation == 15)
    }
}