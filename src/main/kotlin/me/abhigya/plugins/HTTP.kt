package me.abhigya.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.resources.*

fun Application.configureHTTP(allowOrigins: (String) -> Boolean = { true }) {
    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        anyHost()
        allowHeader("*")
        allowCredentials = true
        allowOrigins(allowOrigins)
    }

    install(Resources)

    install(Compression) {
        gzip {
            priority = 0.9
        }
        deflate {
            priority = 1.0
        }
    }
}
