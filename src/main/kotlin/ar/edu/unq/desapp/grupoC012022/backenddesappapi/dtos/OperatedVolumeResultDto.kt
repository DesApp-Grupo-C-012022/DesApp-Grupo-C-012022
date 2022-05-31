package ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos

import java.time.LocalDateTime

class OperatedVolumeResultDto(
    var user: DeserializableUser,
    var requestTimestamp: LocalDateTime,
    var totalUsd: Long,
    var totalArs: Long,
    var criptoActives: List<OperatedVolumeResultCriptoDto>
)