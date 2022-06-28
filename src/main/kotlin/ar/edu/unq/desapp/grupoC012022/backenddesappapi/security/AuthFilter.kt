package ar.edu.unq.desapp.grupoC012022.backenddesappapi.security

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.exceptions.InvalidOrMissingTokenException
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.UserService
import org.springframework.http.HttpHeaders
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class AuthFilter(val userService: UserService) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        if (request.getHeader(HttpHeaders.AUTHORIZATION).isNullOrEmpty()){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or missing token")
            return
        }

        val tokenHeader: String = request.getHeader(HttpHeaders.AUTHORIZATION)
        val chunks = tokenHeader.split(" ").toTypedArray()
        if (chunks.size != 2 || chunks[0].replace(":","") != "Bearer") {
            try {
                userService.validate(chunks[1])
            } catch (e : InvalidOrMissingTokenException) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.message)
                return
            }
        }

        filterChain.doFilter(request, response)
    }

}
