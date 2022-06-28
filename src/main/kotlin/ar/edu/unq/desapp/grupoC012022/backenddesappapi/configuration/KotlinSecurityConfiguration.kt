package ar.edu.unq.desapp.grupoC012022.backenddesappapi.configuration

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.security.AuthFilter
import ar.edu.unq.desapp.grupoC012022.backenddesappapi.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class KotlinSecurityConfiguration : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var userService: UserService

    override fun configure(http: HttpSecurity) {
        http
            .csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/**").permitAll()
            .anyRequest().authenticated()

        http.requestMatchers()
            .antMatchers("/users","/users/**", "/transactions","/transactions/**", "/orders","/orders/**", "/currencies","/currencies/**")
            .and()
            .addFilterBefore(AuthFilter(userService),UsernamePasswordAuthenticationFilter::class.java)

    }
}