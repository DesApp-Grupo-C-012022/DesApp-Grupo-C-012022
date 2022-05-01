package ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User

class UserBuilder {
    private var user: User = User(
        0,
        "Name",
        "LastName",
        "email@email.com",
        "Addres 123",
        "Super#Strong123Pass",
        "MercadoPagoCVU12345678",
        "Address1",
        100
    )

    fun firstName(firstName: String): UserBuilder {
        user.firstName = firstName
        return this
    }

    fun lastName(lastName: String): UserBuilder {
        user.lastName = lastName
        return this
    }

    fun email(email: String): UserBuilder {
        user.email = email
        return this
    }

    fun homeAddress(homeAddress: String): UserBuilder {
        user.homeAddress = homeAddress
        return this
    }

    fun password(password: String): UserBuilder {
        user.password = password
        return this
    }

    fun mercadoPagoCVU(mercadoPagoCVU: String): UserBuilder {
        user.mercadoPagoCVU = mercadoPagoCVU
        return this
    }

    fun walletAddress(walletAddress: String): UserBuilder {
        user.walletAddress = walletAddress
        return this
    }

    fun reputation(reputation: Int): UserBuilder {
        user.reputation = reputation
        return this
    }

    fun id(id: Int): UserBuilder {
        user.id = id
        return this
    }

    fun build(): User {
        return user
    }

    fun createUserWithValues(): UserBuilder {
        user = User(
            0,
            "Name",
            "LastName",
            "email@email.com",
            "Addres 123",
            "Super#Strong123Pass",
            "MercadoPagoCVU12345678",
            "Address1",
            100
        )
        return this
    }
}