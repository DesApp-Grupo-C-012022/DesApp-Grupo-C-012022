package ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency

class OperatedVolumeResultCriptoDto(
    var currency: Currency,
    var nominalQuantity: Long,
    var arsPrice: Long,
)