package ar.edu.unq.desapp.grupoC012022.backenddesappapi.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
TODO: The price is currently declared as a string because of Jackson mapping. The JSON
    received from binance sends the price as a string and jackson automatically maps it
    to a string. Possible solution: Create a custom serializer for the currency class.
*/
class Currency(@JsonProperty("symbol") var ticker: String, @JsonProperty("price") var usdPrice: String) {
}