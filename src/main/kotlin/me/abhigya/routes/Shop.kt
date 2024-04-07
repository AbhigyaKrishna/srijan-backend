package me.abhigya.routes

import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.abhigya.model.Artist
import me.abhigya.model.ShopItem
import me.abhigya.schemas.Shop
import me.abhigya.schemas.ShopTable
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.map
import org.ktorm.entity.sequenceOf

fun Route.shop(database: Database) {
    route("/shop") {
        get<ShopRoute.Get.ById> {
            val item = database.sequenceOf(ShopTable)
                .firstOrNull { table -> table.id eq it.id }
                ?: return@get call.respondText("Item not found", status = HttpStatusCode.NotFound)

            call.respond(item.createModel())
        }

        get<ShopRoute.Get.All> {
            val items = database.sequenceOf(ShopTable)
                .map(Shop::createModel)

            call.respond(items)
        }
    }
}

class ShopRoute {
    @Resource("get")
    class Get {
        @Resource("{id}")
        class ById(val parent: Get = Get(), val id: Int)

        @Resource("all")
        class All(val parent: Get = Get())
    }
}

fun Shop.createModel(): ShopItem {
    return ShopItem(
        id = id,
        name = name,
        artist = Artist(
            id = artist.id,
            name = artist.name,
            bio = artist.bio
        ),
        description = description,
        price = price,
        art1 = pic1,
        art2 = pic2
    )
}