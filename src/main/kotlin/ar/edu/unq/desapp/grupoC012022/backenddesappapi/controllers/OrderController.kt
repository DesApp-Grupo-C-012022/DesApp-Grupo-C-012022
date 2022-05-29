package ar.edu.unq.desapp.grupoC012022.backenddesappapi.controllers

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos.OrderDto
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Order
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class OrderController {

    @Autowired
    lateinit var orderService: OrderService

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(
        method = [RequestMethod.POST],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun create(@RequestBody orderDto: OrderDto) : Order {
        return orderService.create(orderDto)
    }

}