package ar.edu.unq.desapp.grupoC012022.backenddesappapi.controllers

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.CurrencyService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CurrencyController(val currencyService: CurrencyService) {

    @GetMapping("/criptos")
    @ResponseBody
    fun getCriptos(@RequestParam(required = false) referenceCurrency: String?): List<Currency?> {
        return currencyService.getCurrencies(referenceCurrency)
    }
}