package ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Price
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User
import java.time.LocalDateTime

class OperatedVolumeResultCriptoDto(
    var currency: Currency,
    var nominalQuantity: Long,
    var arsPrice: Long,
)