package ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CurrencyRepository : JpaRepository<Currency, Int> {
    fun findByTickerAndLatest(ticker: String,latest: Boolean): Currency?
}