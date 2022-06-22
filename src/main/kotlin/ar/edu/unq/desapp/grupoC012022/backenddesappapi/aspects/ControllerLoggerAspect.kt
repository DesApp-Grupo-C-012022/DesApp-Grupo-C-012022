package ar.edu.unq.desapp.grupoC012022.backenddesappapi.aspects

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class ControllerLoggerAspect {

    private val logger = LoggerFactory.getLogger(ControllerLoggerAspect::class.java)
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
        logger.info("----------Start log----------")
        logger.info("Class: ${joinPoint.target.javaClass}")
        logger.info("Method: ${joinPoint.signature.name}")
        logger.info("Arguments: ${jacksonObjectMapper().writeValueAsString(joinPoint.args[0])}")
        logger.info("Execution time: $diff" + "ms")
        logger.info("----------End log----------")
    }
}