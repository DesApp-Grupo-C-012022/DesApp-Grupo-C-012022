package ar.edu.unq.desapp.grupoC012022.backenddesappapi.security

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class AuthFilter : OncePerRequestFilter() {

    @Autowired
    lateinit var userService: UserService

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        var uri = request.requestURI
        if (request.getHeader(HttpHeaders.AUTHORIZATION).isNullOrEmpty())
            throw IOException()

        val tokenHeader: String = request.getHeader(HttpHeaders.AUTHORIZATION)
        val chunks = tokenHeader.split(" ").toTypedArray()
        if (chunks.size != 2 || chunks[0] != "Bearer" || userService.validate(chunks[1]) == null)
            throw IOException()
        }
}
