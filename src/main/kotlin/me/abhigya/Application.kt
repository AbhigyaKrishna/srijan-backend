package me.abhigya

import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.jetty.*
import me.abhigya.plugins.*
import toothpick.configuration.Configuration
import toothpick.ktp.KTP

fun main() {
    embeddedServer(Jetty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val dotEnv = dotenv()

    val isDevelopment = dotEnv["DEVELOPMENT"] == "DEV"

    KTP.setConfiguration(
        if (isDevelopment)
            Configuration.forDevelopment()
        else
            Configuration.forProduction()
    )

    configureSerialization()

    configureDatabases(DatabaseConfig.fromEnv(dotEnv))

    configureMonitoring(isDevelopment)

    configureHTTP()

    configureRouting()
}
