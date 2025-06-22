package com.tarifit.user.config

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Configuration
class FeignConfig {
    
    @Bean
    fun requestInterceptor(): RequestInterceptor {
        return RequestInterceptor { template: RequestTemplate ->
            val requestAttributes = RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes
            requestAttributes?.request?.let { request ->
                val authorization = request.getHeader("Authorization")
                if (authorization != null) {
                    template.header("Authorization", authorization)
                }
                
                val userIdHeader = request.getHeader("X-User-Id")
                if (userIdHeader != null) {
                    template.header("X-User-Id", userIdHeader)
                }
            }
        }
    }
}