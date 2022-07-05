package ar.edu.unq.desapp.grupoC012022.backenddesappapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@EnableWebMvc
@SpringBootApplication
class CriptoP2PApplication

fun main(args: Array<String>) {
	runApplication<CriptoP2PApplication>(*args)
}
