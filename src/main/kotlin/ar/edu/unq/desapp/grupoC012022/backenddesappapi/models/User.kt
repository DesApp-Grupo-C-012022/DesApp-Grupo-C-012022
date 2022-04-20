package ar.edu.unq.desapp.grupoC012022.backenddesappapi.models

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.exceptions.InvalidPropertyException
import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    id: Int,
    firstName: String,
    lastName: String,
    email: String,
    homeAddress: String,
    password: String,
    mercadoPagoCVU: String,
    walletAddress: String,
    reputation: Int
) {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    var id: Int = 0

    @Column(nullable = false)
    var firstName: String = ""
        set(value) {
            field = value
            validateFirstName()
        }

    @Column(nullable = false)
    var lastName: String = ""
        set(value) {
            field = value
            validateLastName()
        }

    @Column(nullable = false, unique = true)
    var email: String = ""
        set(value) {
            field = value
            validateEmail()
        }

    @Column(nullable = false)
    var homeAddress: String = ""
        set(value) {
            field = value
            validateHomeAddres()
        }

    @Column(nullable = false, name = "mercado_pago_cvu")
    var mercadoPagoCVU: String = ""
        set(value) {
            field = value
            validateMPCVU()
        }


    @Column(nullable = false)
    var password: String = ""
        set(value) {
            field = value
            validatePass()
        }

    @Column(nullable = false)
    var walletAddress: String = ""
        set(value) {
            field = value
            validateWalletAddress()
        }
    
    @Column(nullable = false)
    var reputation: Int = 0
        set(value) {
            field = value
        }

    private fun validateFirstName() {
        if (firstName.length < 3)
            throw InvalidPropertyException()
        if (firstName.length > 30)
            throw InvalidPropertyException()
    }

    private fun validateLastName() {
        if (lastName.length < 3)
            throw InvalidPropertyException()
        if (lastName.length > 30)
            throw InvalidPropertyException()
    }

    private fun validateEmail() {
        if (email.isEmpty())
            throw InvalidPropertyException()
        if (!isEmailValid(email))
            throw InvalidPropertyException()
    }

    private fun validateHomeAddres() {
        if (homeAddress.length < 10)
            throw InvalidPropertyException()
        if (homeAddress.length > 30)
            throw InvalidPropertyException()
        if (homeAddress.isEmpty())
            throw InvalidPropertyException()
    }

    private fun validatePass() {
        if (password.isEmpty())
            throw InvalidPropertyException()
        if (!isPassStrong(password))
            throw InvalidPropertyException()
    }

    private fun validateMPCVU() {
        if (mercadoPagoCVU.length != 22)
            throw InvalidPropertyException()
    }

    private fun validateWalletAddress() {
        if (walletAddress.length != 8)
            throw InvalidPropertyException()
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