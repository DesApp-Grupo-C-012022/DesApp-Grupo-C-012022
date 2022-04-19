package ar.edu.unq.desapp.grupoC012022.backenddesappapi.exceptions

class CurrencyNotSupportedException(override val message: String= "Currency not supported") : Exception(message) {
}