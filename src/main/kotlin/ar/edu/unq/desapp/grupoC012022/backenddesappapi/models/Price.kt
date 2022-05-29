package ar.edu.unq.desapp.grupoC012022.backenddesappapi.models

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions.InvalidPropertyException
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.swagger.v3.oas.annotations.media.Schema
import org.joda.time.LocalDateTime
import javax.persistence.*


@Entity
@Table(name = "prices")
@JsonIgnoreProperties(value = ["id"], allowGetters = true)
class Price(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @field:Schema(hidden = true) var id: Int? = 0,
    @OneToOne @JoinColumn(name = "id", nullable = false) var bidCurrency: Currency,
    sellingPrice: Long,
    @OneToOne @JoinColumn(name = "id", nullable = false)  var askCurrency: Currency
) {
    @Column(nullable = false) val timestamp: LocalDateTime = LocalDateTime.now()

    init{
        validateSellingPrice()
    }

    @Column(nullable = false)
    var sellingPrice: Long = sellingPrice
        set(value) {
            field = value
            validateSellingPrice()
        }

    private fun validateSellingPrice() {
        if (sellingPrice < 0)
            throw InvalidPropertyException()
    }
}