package ar.edu.unq.desapp.grupoC012022.backenddesappapi.aspects

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.User
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import org.apache.logging.log4j.LogManager
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.json.JSONObject
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Aspect
@Component
class ControllerLoggerAspect {

    companion object {
        var user : User? = null
    }

    private val logger = LogManager.getLogger(ControllerLoggerAspect::class.java)
    private var initialTime: Long = 0

    @Pointcut("within(ar.edu.unq.desapp.grupoC012022.backenddesappapi.controllers..*)")
    fun loggerStarter(){}

    @Before("loggerStarter()")
    fun beforeLoggerStarter() {
        this.initialTime = System.currentTimeMillis()
    }

    @After("loggerStarter()")
    fun afterLoggerStarter(joinPoint: JoinPoint) {
        val finishTime = System.currentTimeMillis()
        val diff = finishTime - this.initialTime
        val json = JSONObject()
        val filterProvider = SimpleFilterProvider()
        filterProvider.addFilter("userFilter", SimpleBeanPropertyFilter.serializeAllExcept("password"))
        val mapper = ObjectMapper().setFilterProvider(filterProvider)
        json.put("Timestamp", LocalDateTime.now())
        json.put("Class", joinPoint.target.javaClass)
        json.put("Method", joinPoint.signature.name)
        if (user != null) {
            json.put("User", user!!.id)
        }
        json.put("Execution time", "$diff" + "ms")
        if (joinPoint.args.isNotEmpty()) {
            json.put("Arguments", mapper.writeValueAsString(joinPoint.args))
        }
        logger.info(json)
    }
}