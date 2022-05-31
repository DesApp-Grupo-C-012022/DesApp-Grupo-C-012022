package ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface TransactionRepository : JpaRepository<Transaction, Int> {

    @Query("select * from transactions where id = ?1 and timestamp between ?2 and ?3", nativeQuery = true)
    fun findTransactionsBetweenDates(id: Int, fromDate: LocalDateTime, toDate: LocalDateTime): List<Transaction>
}