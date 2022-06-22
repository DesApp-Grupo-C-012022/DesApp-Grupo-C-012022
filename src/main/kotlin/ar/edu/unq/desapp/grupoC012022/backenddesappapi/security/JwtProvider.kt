package ar.edu.unq.desapp.grupoC012022.backenddesappapi.security

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct

@Component
class JwtProvider {

    @Value("\${jwt.secret}")
    lateinit var secret: String

    @PostConstruct
    fun init(){
        secret = Base64.getEncoder().encodeToString(secret.toByteArray())
    }

    fun createToken(user: User): String{
        var claims = Jwts.claims().setSubject(user.email)
        claims["id"] = user.id

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(DateTime.now().toDate())
                .setExpiration(DateTime.now().plusMinutes(5).toDate())
                .signWith(SignatureAlgorithm.HS256, secret)
            .compact()
    }

    fun validate(token: String): Boolean {
        return try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getEmailFronToken(token: String): String{
        return try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body.subject
        } catch (e: Exception){
            "Bad token"
        }
    }

}