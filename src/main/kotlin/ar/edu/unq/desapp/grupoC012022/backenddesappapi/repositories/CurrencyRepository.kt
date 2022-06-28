package ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface CurrencyRepository : JpaRepository<Currency, Int> {
    fun findFirstByTickerOrderByTimestampDesc(ticker: String): Currency?
    fun findByTickerAndTimestampGreaterThanOrderByTickerAscTimestampDesc(ticker: String, time: LocalDateTime): List<Currency>
}