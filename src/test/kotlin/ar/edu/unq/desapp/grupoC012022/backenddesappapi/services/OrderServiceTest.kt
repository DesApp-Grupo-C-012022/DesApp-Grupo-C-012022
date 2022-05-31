package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.OrderBuilder
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders.UserBuilder
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos.OrderDto
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.helpers.MockitoHelper
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories.OrderRepository
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.InvalidPropertyException
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.validators.OrderValidator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class OrderServiceTest {

	@Mock
	private lateinit var orderValidatorMock: OrderValidator

	@Mock
	private lateinit var orderRepositoryMock: OrderRepository

	@Mock
	private lateinit var userServiceMock: UserService

	@Autowired
	@InjectMocks
	private lateinit var subject : OrderService
	private lateinit var order: OrderBuilder
	private val orderBuilder = OrderBuilder()
	private lateinit var user: UserBuilder
	private val userBuilder = UserBuilder()

	@BeforeEach
	fun setUp() {
		MockitoAnnotations.openMocks(this)
		order = orderBuilder
			.createOrderWithValues()
		user = userBuilder
			.createUserWithValues()
	}

	@Test
	fun saveOrderTest() {
		val orderToSave = order.build()
		val dborder = order.id(1).build()
		`when`(orderRepositoryMock.save(orderToSave)).thenReturn(dborder)
		subject.save(orderToSave)
		verify(orderRepositoryMock, times(1)).save(orderToSave)
	}

	@Test
	fun saveInvalidPropertyUserTest() {
		val orderToSave = order.build()
		`when`(orderValidatorMock.validateOrder(orderToSave)).thenThrow(InvalidPropertyException::class.java)
		assertThrows<InvalidPropertyException> { subject.save(orderToSave) }
		verify(orderRepositoryMock, times(0)).save(orderToSave)
	}

	@Test
	fun createFromDtoTest() {
		val orderToCreate = order.build()
		val dborder = order.id(1).build()
		val user = user.id(1).build()
		var orderDto = OrderDto()
		orderDto.price = orderToCreate.price.sellingPrice
		orderDto.ticker	= orderToCreate.price.bidCurrency.ticker
		orderDto.quantity = orderToCreate.quantity
		orderDto.userFirstName = orderToCreate.user.firstName
		orderDto.userLastname = orderToCreate.user.lastName
		orderDto.operation = orderToCreate.operation

		`when`(orderRepositoryMock.save(MockitoHelper.anyObject())).thenReturn(dborder)
		`when`(userServiceMock.getByName(MockitoHelper.anyObject(),MockitoHelper.anyObject())).thenReturn(user)
		val createdOrder = subject.create(orderDto)
		assert(createdOrder.price.sellingPrice == orderToCreate.price.sellingPrice)
		assert(createdOrder.price.bidCurrency == orderToCreate.price.bidCurrency)
		assert(createdOrder.quantity == orderToCreate.quantity)
		assert(createdOrder.user.firstName == orderToCreate.user.firstName)
		assert(createdOrder.user.lastName == orderToCreate.user.lastName)
		assert(createdOrder.operation == orderToCreate.operation)
		verify(orderRepositoryMock, times(1)).save(MockitoHelper.anyObject())
	}

	@Test
	fun getActivesTest() {
		`when`(orderRepositoryMock.findByIsActive(true)).thenReturn(listOf(order.id(1).build(),order.id(2).build()))
		val orders = this.subject.getActives()
		Assertions.assertEquals(2, orders.size)
	}

	@Test
	fun toOfferedDtoTest() {
		val order = order.build()
		val orderDto = this.subject.toOfferedDto(order)
		Assertions.assertEquals(order.price.timestamp, orderDto.timestamp)
		Assertions.assertEquals(order.price.bidCurrency.ticker, orderDto.ticker)
		Assertions.assertEquals(order.quantity, orderDto.quantity )
		Assertions.assertEquals(order.price.sellingPrice, orderDto.price)
		Assertions.assertEquals(order.totalArsPrice, orderDto.arsPrice)
		Assertions.assertEquals(order.user.firstName, orderDto.userFirstName)
		Assertions.assertEquals(order.user.lastName, orderDto.userLastname)
		Assertions.assertEquals(order.user.reputation, orderDto.reputation)
		Assertions.assertEquals(order.user.operationsAmount, orderDto.operationsAmount)
	}
}
