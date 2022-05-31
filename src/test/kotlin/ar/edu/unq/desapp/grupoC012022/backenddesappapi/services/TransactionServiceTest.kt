package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.*
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos.TransactionDto
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Operation
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Order
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.TransactionAction
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.TransactionRepository
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.CantCancelOrderThatIsNotYoursException
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.transaction.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class TransactionServiceTest {

	@Mock
	private lateinit var transactionActionFactoryMock: TransactionActionFactory
	@Mock
	private lateinit var userServiceMock: UserService
	@Mock
	private lateinit var orderServiceMock: OrderService
	@Mock
	private lateinit var currencyService: CurrencyService
	@Mock
	private lateinit var transactionRepository: TransactionRepository
	@Spy
	private lateinit var mercadoPagoApiMock: MercadoPagoApi
	@Spy
	private lateinit var criptoExchangerMock: CriptoExchanger
	private lateinit var userFromOrder: User
	private lateinit var executingUser: User
	private lateinit var transactionDto: TransactionDto
	private lateinit var dbOrder: Order

	@Autowired
	@InjectMocks
	private lateinit var subject : TransactionService
	@Autowired
	@InjectMocks
	private lateinit var transactionConfirmTransferMock: TransactionConfirmTransfer
	@Autowired
	@InjectMocks
	private lateinit var transactionConfirmReceptionMock: TransactionConfirmReception
	@Autowired
	@InjectMocks
	private lateinit var transactionCancelMock: TransactionCancel

	private val orderBuilder = OrderBuilder()
	private val userBuilder = UserBuilder()
	private val priceBuilder = PriceBuilder()
	private val currencyBuilder = CurrencyBuilder()
	private val transactionBuilder = TransactionBuilder()

	@BeforeEach
	fun setUp() {
		MockitoAnnotations.openMocks(this)
		userFromOrder = userBuilder.createUserWithValues().id(1).mercadoPagoCVU("MercadoPagoCvuUserOrder").build()
		executingUser = userBuilder.createUserWithValues().id(2).mercadoPagoCVU("MercadoPagoCvuExecutingOrder").build()
	}

	@Test
	fun processTransactionWithASellOrderTest() {
		prepareTestContextForTransaction(2, TransactionAction.CONFIRM_TRANSFER, Operation.SELL, transactionConfirmTransferMock)
		`when`(currencyService.getCurrency("BTC")).thenReturn(Currency(ticker = "BTC", usdPrice = 50000.0))
		subject.processTransaction(transactionDto)
		verify(mercadoPagoApiMock, times(1))
			.transferMoney(100000, executingUser.mercadoPagoCVU, userFromOrder.mercadoPagoCVU)
		verify(criptoExchangerMock, times(1))
			.transferCriptoCurrency(99000, "USDT", userFromOrder.walletAddress, executingUser.walletAddress)
	}

	@Test
	fun processTransactionWithABuyOrderTest() {
		prepareTestContextForTransaction(2, TransactionAction.CONFIRM_RECEPTION, Operation.BUY, transactionConfirmReceptionMock)
		`when`(currencyService.getCurrency("BTC")).thenReturn(Currency(ticker = "BTC", usdPrice = 50000.0))
		subject.processTransaction(transactionDto)
		verify(mercadoPagoApiMock, times(1))
			.transferMoney(100000, userFromOrder.mercadoPagoCVU, executingUser.mercadoPagoCVU)
		verify(criptoExchangerMock, times(1))
			.transferCriptoCurrency(99000, "USDT", executingUser.walletAddress, userFromOrder.walletAddress)
	}

	@Test
	fun processTransactionWithACancelTransactionActionAndAUserDifferentFromTheOrder() {
		prepareTestContextForTransaction(2, TransactionAction.CANCEL, Operation.BUY, transactionCancelMock)
		assertThrows<CantCancelOrderThatIsNotYoursException> { subject.processTransaction(transactionDto) }
	}

	@Test
	fun processTransactionWithACancelTransactionActionAndTheSameUserAsInTheOrder() {
		prepareTestContextForTransaction(1, TransactionAction.CANCEL, Operation.BUY, transactionCancelMock)
		subject.processTransaction(transactionDto)
		verify(orderServiceMock, times(1)).save(dbOrder)
	}

	private fun prepareTestContextForTransaction(executingUserId: Int, transactionAction: TransactionAction, operation: Operation, transactionMock: TransactionActionBase) {
		transactionDto = TransactionDto(executingUserId, 1, transactionAction)
		dbOrder = orderBuilder
			.createOrderWithValues()
			.id(1)
			.operation(operation)
			.user(userFromOrder)
			.price(
				priceBuilder
					.createPriceWithValues()
					.askCurrency(currencyBuilder.createCurrencyWithValues().ticker("USDT").build())
					.timestamp(
						LocalDateTime.now().minusMinutes(20)
					).build()
			)
			.totalArsPrice(100000)
			.quantity(99000)
			.build()
		`when`(orderServiceMock.findById(1)).thenReturn(dbOrder)
		`when`(userServiceMock.findById(1)).thenReturn(userFromOrder)
		`when`(userServiceMock.findById(2)).thenReturn(executingUser)
		`when`(transactionActionFactoryMock.createFromAction(transactionAction)).thenReturn(transactionMock)
		`when`(userServiceMock.save(userFromOrder)).thenReturn(userFromOrder)
		`when`(userServiceMock.save(executingUser)).thenReturn(executingUser)
		`when`(transactionRepository.save(any())).thenReturn(transactionBuilder.createTransactionWithValues().build())
	}
}
