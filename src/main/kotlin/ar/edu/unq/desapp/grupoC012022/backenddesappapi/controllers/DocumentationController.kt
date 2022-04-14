package ar.edu.unq.desapp.grupoC012022.backenddesappapi.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
/*
To avoid going to /swagger-ui/index.html, this controller adds a
/swagger endpoint which redirects to the aforementioned path
*/
class DocumentationController {

    @GetMapping("/swagger")
    fun swagger(): ModelAndView {
        return ModelAndView("redirect:/swagger-ui/index.html")
    }
}