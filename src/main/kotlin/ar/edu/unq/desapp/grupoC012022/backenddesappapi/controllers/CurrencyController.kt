package ar.edu.unq.desapp.grupoC012022.backenddesappapi.controllers

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.CurrencyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class CurrencyController {

    @Autowired
    private lateinit var currencyService: CurrencyService

    @GetMapping("/currencies")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun getCurrencies(): List<Currency?> {
        return currencyService.getCurrencies()
    }

    @PostMapping("/currencies")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun updateCurrencies(): List<Currency?> {
        return currencyService.updateCurrencies()
    }
}