package ar.edu.unq.desapp.grupoC012022.backenddesappapi.models

class User(var FirstName: String, var LastName: String, var Email: String, var HomeAddress: String, var Password: String, var MercadoPagoCVU: String, var WalletAddress: String, var Reputation: Int) {

    init {
        validateFirstName()
        validateLastName()
        validateEmail()
        validateHomeAddres()
        validatePass()
        validateMPCVU()
    }

    //TODO: Modificar excepciones lanzadas

    private fun validateFirstName() {
        if (FirstName.length < 3)
            throw IllegalArgumentException()
        if (FirstName.length > 30)
            throw IllegalArgumentException()
    }

    private fun validateLastName() {
        if (LastName.length < 3)
            throw IllegalArgumentException()
        if (LastName.length > 30)
            throw IllegalArgumentException()
    }

    private fun validateEmail() {
        if (Email.isNullOrEmpty())
            throw IllegalArgumentException()
        if (!isEmailValid(Email))
            throw IllegalArgumentException()
    }

    private fun validateHomeAddres() {
        if (HomeAddress.length < 10)
            throw IllegalArgumentException()
        if (HomeAddress.length > 30)
            throw IllegalArgumentException()
        if (HomeAddress.isNullOrEmpty())
            throw IllegalArgumentException()
    }

    private fun validatePass() {
        if (Password.isNullOrEmpty())
            throw IllegalArgumentException()
        if (!isPassStrong(Password))
            throw IllegalArgumentException()
    }

    private fun validateMPCVU() {
        if (MercadoPagoCVU.length != 22)
            throw IllegalArgumentException()
    }

    private fun isEmailValid(email: String): Boolean {
        val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return EMAIL_REGEX.toRegex().matches(email)
    }

    private fun isPassStrong(email: String): Boolean {
        val PASS_REGEX = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$ %^&*-]).{8,}\$"
        return PASS_REGEX.toRegex().matches(email)
    }



}