package ar.edu.unq.desapp.grupoC012022.backenddesappapi.models

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.InvalidPropertyException
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.swagger.v3.oas.annotations.media.Schema
import javax.persistence.*


@Entity
@Table(name = "orders")
@JsonIgnoreProperties(value = ["id"], allowGetters = true)
class Order(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @field:Schema(hidden = true) var id: Int = 0,
    @Column(nullable = false) var quantity: Long,
    @OneToOne @JoinColumn(name = "id", nullable = false) var price: Price,
    @Column(nullable = false) var totalArsPrice: Long,
    @OneToOne @JoinColumn(name = "id", nullable = false) var user: User,
    @Column(nullable = false) var operation: Operation,
    @Column(nullable = false) var isActive: Boolean = true,
){
    fun validate() {
        validateQuantity()
        validateArsPrice()
    }

    private fun validateArsPrice() {
        if(totalArsPrice < 0)
            throw InvalidPropertyException()
    }

    private fun validateQuantity() {
        if (quantity < 0)
            throw InvalidPropertyException()
    }
}