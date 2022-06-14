package ar.edu.unq.desapp.grupoC012022.backenddesappapi.jobs

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.CurrencyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@EnableScheduling
class CurrencyPriceJob {

    @Autowired
    private lateinit var currencyService: CurrencyService

    @Scheduled(cron = "0 0/10 * * * ?")
    fun run() {
        currencyService.updateCurrencies()
    }
}