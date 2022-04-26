package ar.edu.unq.desapp.grupoC012022.backenddesappapi.models

class User(FirstName: String, LastName: String, Email: String, HomeAddress: String, Password: String, MercadoPagoCVU: String, WalletAddress: String, var Reputation: Int) {

    //TODO: Modificar excepciones lanzadas

    var FirstName: String = FirstName
        set(value) {
            field = value
            validateFirstName()
        }

    var LastName: String = LastName
        set(value) {
            field = value
            validateLastName()
        }

    var Email: String = Email
        set(value) {
            field = value
            validateEmail()
        }

    var HomeAddress: String = HomeAddress
        set(value) {
            field = value
            validateHomeAddres()
        }


    var MercadoPagoCVU: String = MercadoPagoCVU
        set(value) {
            field = value
            validateMPCVU()
        }


    var Password: String = Password
        set(value) {
            field = value
            validatePass()
        }

    var WalletAddress: String = WalletAddress
        set(value) {
            field = value
            validateWalletAddress()
        }

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
        if (Email.isEmpty())
            throw IllegalArgumentException()
        if (!isEmailValid(Email))
            throw IllegalArgumentException()
    }

    private fun validateHomeAddres() {
        if (HomeAddress.length < 10)
            throw IllegalArgumentException()
        if (HomeAddress.length > 30)
            throw IllegalArgumentException()
        if (HomeAddress.isEmpty())
            throw IllegalArgumentException()
    }

    private fun validatePass() {
        if (Password.isEmpty())
            throw IllegalArgumentException()
        if (!isPassStrong(Password))
            throw IllegalArgumentException()
    }

    private fun validateMPCVU() {
        if (MercadoPagoCVU.length != 22)
            throw IllegalArgumentException()
    }

    private fun validateWalletAddress() {
        if (WalletAddress.length != 8)
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