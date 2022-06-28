package ar.edu.unq.desapp.grupoC012022.backenddesappapi.controllers

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.CurrencyService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.Authorization
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class CurrencyController {

    @Autowired
    private lateinit var currencyService: CurrencyService

    @ApiOperation(value = "", authorizations = [Authorization(value = "jwtToken")])
    @GetMapping("/currencies")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun getCurrencies(): List<Currency?> {
        return currencyService.getCurrencies()
    }

    @ApiOperation(value = "", authorizations = [Authorization(value = "jwtToken")])
    @PostMapping("/currencies")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun updateCurrencies(): List<Currency?> {
        return currencyService.updateCurrencies()
    }

    @ApiOperation(value = "", authorizations = [Authorization(value = "jwtToken")])
    @GetMapping("/currencies/prices")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun getPrices(): List<Currency> {
        return currencyService.getPrices()
    }
}