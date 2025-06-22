package com.tarifit.user

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.mongodb.config.EnableMongoAuditing

@SpringBootApplication
@EnableFeignClients
@EnableMongoAuditing
class TarifitUserServiceApplication

fun main(args: Array<String>) {
    runApplication<TarifitUserServiceApplication>(*args)
}