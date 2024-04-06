package me.abhigya

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.jetty.*
import me.abhigya.plugins.*

fun main() {
    embeddedServer(Jetty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureRouting()
}
