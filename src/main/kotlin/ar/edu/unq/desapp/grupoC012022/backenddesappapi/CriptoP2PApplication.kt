package ar.edu.unq.desapp.grupoC012022.backenddesappapi

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.jobs.CurrencyPriceJob
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CriptoP2PApplication

fun main(args: Array<String>) {
	runApplication<CriptoP2PApplication>(*args).run { CurrencyPriceJob().run() }
}
