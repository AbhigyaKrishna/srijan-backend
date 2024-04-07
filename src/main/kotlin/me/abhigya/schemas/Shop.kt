package me.abhigya.schemas

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.double
import org.ktorm.schema.int
import org.ktorm.schema.varchar

interface Shop : Entity<Shop> {
    companion object : Entity.Factory<Shop>()

    val id: Int
    var name: String
    var artistId: Int
    val artist: Artist
    var description: String
    var price: Double
    var pic1: String
    var pic2: String
}

object ShopTable : Table<Shop>("shopitems") {
    val id = int("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val artistId = int("artist_id").bindTo { it.artistId }.references(ArtistsTable) { it.artist }
    val description = varchar("description").bindTo { it.description }
    val price = double("price").bindTo { it.price }
    val pic1 = varchar("pic1").bindTo { it.pic1 }
    val pic2 = varchar("pic2").bindTo { it.pic2 }
}