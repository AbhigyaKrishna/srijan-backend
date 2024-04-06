package me.abhigya.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.request.*
import org.slf4j.event.Level

fun Application.configureMonitoring(isDevelopment: Boolean) {
    install(CallLogging) {
        level = if (isDevelopment) Level.TRACE else Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }
}
