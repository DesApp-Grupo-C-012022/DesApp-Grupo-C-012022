package ar.edu.unq.desapp.grupoC012022.backenddesappapi.builders

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User

class UserBuilder {
    private var user: User = User("Name", "LastName", "email@email.com", "Addres 123", "Super#Strong123Pass", "MercadoPagoCVU12345678", "Address1", 100)

    fun firstName(firstName: String): UserBuilder {
        user.FirstName = firstName
        return this
    }

    fun lastName(lastName: String): UserBuilder {
        user.LastName = lastName
        return this
    }

    fun email(email: String): UserBuilder {
        user.Email = email
        return this
    }

    fun homeAddress(homeAddress: String): UserBuilder {
        user.HomeAddress = homeAddress
        return this
    }

    fun password(password: String): UserBuilder {
        user.Password = password
        return this
    }

    fun mercadoPagoCVU(mercadoPagoCVU: String): UserBuilder {
        user.MercadoPagoCVU = mercadoPagoCVU
        return this
    }

    fun walletAddress(walletAddress: String): UserBuilder {
        user.WalletAddress = walletAddress
        return this
    }

    fun reputation(reputation: Int): UserBuilder {
        user.Reputation = reputation
        return this
    }

    fun build(): User {
        return user;
    }

    fun createUserWithValues(): UserBuilder {
        user = User("Name", "LastName", "email@email.com", "Addres 123", "Super#Strong123Pass", "MercadoPagoCVU12345678", "Address1", 100)
        return this;
    }
}