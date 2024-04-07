package me.abhigya.routes

import io.ktor.server.routing.*
import org.ktorm.database.Database

fun Route.events(database: Database) {
    route("/events") {

    }
}

class EventRoute {

}