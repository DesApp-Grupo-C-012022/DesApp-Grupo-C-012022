package ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.exceptions

class CurrencyNotSupportedException(override val message: String= "Currency not supported") : Exception(message) {
}