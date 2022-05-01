package ar.edu.unq.desapp.grupoC012022.backenddesappapi.configuration

import ar.edu.unq.desapp.grupoC012022.backenddesappapi.models.Currency
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class RedisConfiguration {
    @Bean
    fun jedisConnectionFactory(): JedisConnectionFactory {
        val redisStandaloneConfiguration = RedisStandaloneConfiguration("127.0.0.1", 6379)
        return JedisConnectionFactory(redisStandaloneConfiguration)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Currency>  {
        val template = RedisTemplate<String, Currency>()
        template.setConnectionFactory(jedisConnectionFactory())
        return template
    }
}