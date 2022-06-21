package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.OrderBuilder
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.TransactionBuilder
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.UserBuilder
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Order
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Status
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Transaction
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.TransactionRepository
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.exceptions.CantCancelOrderThatIsNotYoursException
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction.TransactionCancel
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentCaptor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class TransactionCancelTest {

    @Mock
    private lateinit var orderService: OrderService
    @Mock
    private lateinit var userService: UserService
    @Mock
    private lateinit var transactionRepository: TransactionRepository
    @InjectMocks
    private lateinit var subject: TransactionCancel
    private lateinit var executingUser: User
    private lateinit var order: Order

    private val orderBuilder = OrderBuilder()
    private val userBuilder = UserBuilder()
    private val transactionBuilder = TransactionBuilder()

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        executingUser = userBuilder.createUserWithValues().id(1).reputation(50).build()
        order = orderBuilder.createOrderWithValues().user(executingUser).build()
        `when`(userService.save(executingUser)).thenReturn(executingUser)
        `when`(orderService.delete(order)).thenAnswer { }
        `when`(transactionRepository.save(any())).thenReturn(transactionBuilder.createTransactionWithValues().build())
    }

    @Test
    fun whenTransactionCancelProcessTheUserDecreasesItsReputationBy20() {
        subject.process(order, executingUser)
        assert(executingUser.reputation == 30)
        val savedTransaction = ArgumentCaptor.forClass(Transaction::class.java)
        verify(transactionRepository, times(1)).save(savedTransaction.capture())
        assert(Status.CANCELED == savedTransaction.value.status)
    }

    @Test
    fun whenTransactionCancelProcessWithDifferentExecutingUserTheUserDoesNotDecreasesItsReputationBy20() {
        val differentExecutingUser = spy(userBuilder.createUserWithValues().id(2).build())
        assertThrows<CantCancelOrderThatIsNotYoursException> { subject.process(order, differentExecutingUser) }
    }
}