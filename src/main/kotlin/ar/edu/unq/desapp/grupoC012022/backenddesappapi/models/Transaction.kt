package ar.edu.unq.desapp.grupoC012022.backenddesappapi.models

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.InvalidPropertyException
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.swagger.v3.oas.annotations.media.Schema
import org.joda.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "transactions")
@JsonIgnoreProperties(value = ["id"], allowGetters = true)
class Transaction(currency: Currency, quantity: Long, price: Price, amount: Long, user: User, operationQuantity: Long, destinationAddress: String, status: Status) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @field:Schema(hidden = true) var id: Int = 0
    @OneToOne @JoinColumn(name = "id", nullable = false) var currency: Currency = currency
    @Column(nullable = false) var quantity: Long = quantity
    @OneToOne @JoinColumn(name = "id", nullable = false) var price: Price = price
    @Column(nullable = false) var amount: Long = amount
    @OneToOne @JoinColumn(name = "id", nullable = false) var user: User = user
    @Column(nullable = false) var operationQuantity: Long = operationQuantity // Cantidad nominal de cryptoActivo
    @Column(nullable = false) var destinationAddress: String = destinationAddress // TODO: Analizar si tiene sentido modelar Address
    @Column(nullable = false) var status: Status = status
    @Column(nullable = false) val timestamp: LocalDateTime = LocalDateTime.now()

    fun validate() {
        validateQuantity()
        validateAmount()
        validateOperationQuantity()
        validateDestiantionAddress()
    }

    private fun validateQuantity() {
        if(quantity < 0)
            throw InvalidPropertyException()
    }

    private fun validateAmount() {
        if(amount < 0)
            throw InvalidPropertyException()
    }

    private fun validateOperationQuantity() {
        if(operationQuantity < 0)
            throw InvalidPropertyException()
    }

    private fun validateDestiantionAddress() {
        if(destinationAddress.isEmpty())
            throw InvalidPropertyException()
    }
}