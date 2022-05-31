package ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos

import java.time.LocalDate

class OperatedVolumeRequestDto(
    var userId: Int,
    var fromDate: LocalDate,
    var toDate: LocalDate
)