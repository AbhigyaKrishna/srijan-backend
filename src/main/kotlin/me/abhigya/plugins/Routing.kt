package me.abhigya.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import me.abhigya.routes.artist
import me.abhigya.routes.events
import me.abhigya.routes.shop
import org.ktorm.database.Database
import toothpick.ktp.KTP
import toothpick.ktp.extension.getInstance

fun Application.configureRouting() {
    val database = KTP.openRootScope().getInstance<Database>()

    routing {
        artist(database)
        shop(database)
        events(database)
    }
}
