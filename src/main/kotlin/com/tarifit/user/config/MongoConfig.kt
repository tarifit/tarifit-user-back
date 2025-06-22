package com.tarifit.user.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration

@Configuration
class MongoConfig : AbstractMongoClientConfiguration() {
    
    override fun getDatabaseName(): String {
        return "tarifit_users"
    }
}