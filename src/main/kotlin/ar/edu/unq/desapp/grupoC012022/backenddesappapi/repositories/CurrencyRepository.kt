package ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CurrencyRepository: CrudRepository<Currency, String>