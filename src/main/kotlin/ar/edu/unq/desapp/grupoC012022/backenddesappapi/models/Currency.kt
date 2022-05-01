package ar.edu.unq.desapp.grupoC012022.backenddesappapi.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.redis.core.RedisHash
import java.time.LocalDateTime
import javax.persistence.Id

/**
TODO: The price is currently declared as a string because of Jackson mapping. The JSON
    received from binance sends the price as a string and jackson automatically maps it
    to a string. Possible solution: Create a custom serializer for the currency class.
*/
@RedisHash("currency")
@JsonIgnoreProperties(value = ["id"])
class Currency(
    @JsonIgnore @Id @JsonProperty("id") var id: String?,
    @JsonProperty("symbol") var ticker: String,
    @JsonProperty("price") var usdPrice: String,
    @JsonProperty("lastUpdate") var lastUpdate: LocalDateTime?
) {
    init {
        this.lastUpdate = this.lastUpdate ?: LocalDateTime.now()
    }
}
