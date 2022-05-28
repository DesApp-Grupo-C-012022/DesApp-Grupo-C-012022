package ar.edu.unq.desapp.grupoC012022.backenddesappapi.models

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer


class CurrencyDeserializer: StdDeserializer<Currency>(Currency::class.java) {

    override fun deserialize(parser: JsonParser, context: DeserializationContext): Currency {
        val node: JsonNode = parser.codec.readTree(parser)
        val ticker = node.get("symbol").asText()
        val usdPrice = node.get("price").textValue().toDouble()

        return Currency(ticker, usdPrice)
    }
}