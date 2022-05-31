package ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Int> {
    fun findByIsActive(isActive: Boolean): List<Order>
}