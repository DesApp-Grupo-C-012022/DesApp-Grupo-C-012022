package ar.edu.unq.desapp.grupoC012022.backenddesappapi.repositories

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findByEmail(email: String) : User?
    fun findByFirstNameAndLastName(firstName: String, lastname: String): User?
}