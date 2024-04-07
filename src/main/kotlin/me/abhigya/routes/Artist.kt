package me.abhigya.routes

import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.abhigya.model.Artist
import me.abhigya.schemas.ArtTable
import me.abhigya.schemas.ArtistsTable
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.map
import org.ktorm.entity.sequenceOf

fun Route.artist(database: Database) {
    route("/artist") {
        get<ArtistRoute.Get.ById> {
            val artist = database.sequenceOf(ArtistsTable)
                .firstOrNull { table -> table.id eq it.id }
                ?: return@get call.respondText("Artist not found", status = HttpStatusCode.NotFound)

            call.respond(Artist(
                id = artist.id,
                name = artist.name,
                bio = artist.bio
            ))
        }

        get<ArtistRoute.Get.All> {
            val artists = database.sequenceOf(ArtistsTable)
                .map { artist -> Artist(
                    id = artist.id,
                    name = artist.name,
                    bio = artist.bio
                ) }

            call.respond(artists)
        }
    }

    route("/art") {
        get<ArtRoute.GetImg.ByKey> {
            val art = database.sequenceOf(ArtTable)
                .firstOrNull { table -> table.key eq it.key }
                ?: return@get call.respondText("Art not found", status = HttpStatusCode.NotFound)

            call.respondBytes(art.item, ContentType.Image.JPEG, HttpStatusCode.OK)
        }
    }
}

class ArtistRoute {
    @Resource("get")
    class Get {
        @Resource("{id}")
        class ById(val parent: Get = Get(), val id: Int)

        @Resource("all")
        class All(val parent: Get = Get())
    }
}

class ArtRoute {
    @Resource("get_img")
    class GetImg {
        @Resource("{key}")
        class ByKey(val parent: GetImg = GetImg(), val key: String)
    }
}