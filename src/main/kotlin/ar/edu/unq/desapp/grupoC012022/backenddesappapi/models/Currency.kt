package ar.edu.unq.desapp.grupoC012022.backenddesappapi.models

import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(using = CurrencyDeserializer::class)
class Currency(var ticker: String, var usdPrice: Double)