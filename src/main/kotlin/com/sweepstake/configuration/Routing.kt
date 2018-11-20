package com.sweepstake.configuration

import com.sweepstake.client.ClientHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class Routing {

    @Bean
    fun clientsRouter(handler: ClientHandler) = router {
        ("/client" and accept(MediaType.APPLICATION_JSON)).nest {
            POST("/", handler::register)
        }
    }
}