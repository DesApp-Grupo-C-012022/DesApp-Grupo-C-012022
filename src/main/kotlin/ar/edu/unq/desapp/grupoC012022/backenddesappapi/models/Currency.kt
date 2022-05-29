package ar.edu.unq.desapp.grupoC012022.backenddesappapi.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "currencies")
@JsonIgnoreProperties(value = ["id"], allowGetters = true)
@JsonDeserialize(using = CurrencyDeserializer::class)
class Currency(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @field:Schema(hidden = true) var id: Int = 0,
    @Column(nullable = false) var ticker: String,
    @Column(nullable = false) var usdPrice: Double,
    @Column(nullable = false) var latest: Boolean = false
) {

    @Column(nullable = false) val timestamp: LocalDateTime = LocalDateTime.now()
}
