package ar.edu.unq.desapp.grupoC012022.backenddesappapi.controllers

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos.DeserializableUser
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.dtos.TokenDto
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.UserService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.Authorization
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController {

    @Autowired
    lateinit var userService: UserService

    @ApiOperation(value = "", authorizations = [Authorization(value = "jwtToken")])
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun save(@RequestBody user: User) : DeserializableUser? {
        return userService.save(user).toDeserializableUser()
    }

    @ApiOperation(value = "", authorizations = [Authorization(value = "jwtToken")])
    @GetMapping("/users")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun getUsers(): List<DeserializableUser?> {
        return userService.getUsers()
    }


    @PostMapping("/auth/login")
    @ResponseBody
    fun login(@RequestBody dto: DeserializableUser): ResponseEntity<TokenDto>{
        val tokenDto = userService.login(dto)
        if(tokenDto == null)
            ResponseEntity.badRequest().build<TokenDto>()
        return ResponseEntity.ok(tokenDto)
    }

    @PostMapping("/auth/validate")
    @ResponseBody
    fun validate(@RequestBody token: String): ResponseEntity<TokenDto>{
        val tokenDto = userService.validate(token)
        if(tokenDto == null)
            ResponseEntity.badRequest().build<TokenDto>()
        return ResponseEntity.ok(tokenDto)
    }
}
