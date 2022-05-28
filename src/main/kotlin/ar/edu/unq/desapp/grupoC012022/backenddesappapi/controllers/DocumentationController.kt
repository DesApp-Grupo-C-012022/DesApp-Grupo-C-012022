package ar.edu.unq.desapp.grupoC012022.backenddesappapi.controllers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
/*
To avoid going to /swagger-ui/index.html, this controller adds a
/swagger endpoint which redirects to the aforementioned path
*/
class DocumentationController {

    @GetMapping("/swagger")
    @ResponseStatus(code = HttpStatus.PERMANENT_REDIRECT)
    fun swagger(): ModelAndView {
        return ModelAndView("redirect:/swagger-ui/index.html")
    }
}