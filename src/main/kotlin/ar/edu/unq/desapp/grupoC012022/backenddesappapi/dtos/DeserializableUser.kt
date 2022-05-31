package ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos

class DeserializableUser(
    var id: Int,
    var firstName: String,
    var lastName: String,
    var email: String,
    var homeAddress: String,
    var mercadoPagoCVU: String,
    var walletAddress: String,
    var operationsAmount: Int? = 0,
    var reputation: Int? = 0
)