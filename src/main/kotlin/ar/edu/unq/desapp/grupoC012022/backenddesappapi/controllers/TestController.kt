package ar.edu.unq.desapp.grupoC012022.backenddesappapi.controllers

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Cripto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/")
    @ResponseBody
    fun getCriptos(): List<Cripto> {
        val cripto1 = Cripto("usdt", 1)
        val cripto2 = Cripto("btc", 50000)
        return listOf(cripto1, cripto2)
    }
}