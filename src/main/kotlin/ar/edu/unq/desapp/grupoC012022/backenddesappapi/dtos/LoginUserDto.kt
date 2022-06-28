package ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos

import com.fasterxml.jackson.annotation.JsonFilter

@JsonFilter("userFilter")
class LoginUserDto(val email: String, val password: String)