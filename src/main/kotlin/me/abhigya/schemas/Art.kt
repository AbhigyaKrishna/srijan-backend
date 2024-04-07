package me.abhigya.schemas

import org.ktorm.entity.Entity
import org.ktorm.schema.*

interface Art : Entity<Art> {
    companion object : Entity.Factory<Art>()

    var key: String
    var item: ByteArray
}

interface Artist : Entity<Artist> {
    companion object : Entity.Factory<Artist>()

    val id: Int
    var name: String
    var bio: String
}

interface ArtistToArt : Entity<ArtistToArt> {
    companion object : Entity.Factory<ArtistToArt>()

    var artistId: Int
    val artist: Artist
    var artId: String
}

object ArtTable : Table<Art>("art") {
    val key = varchar("key").primaryKey().bindTo { it.key }
    val item = bytes("item").bindTo { it.item }
}

object ArtistsTable : Table<Artist>("artists") {
    val id = int("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val bio = text("bio").bindTo { it.bio }
}

object ArtistToArtTable : Table<ArtistToArt>("artist_to_art") {
    val artistId = int("artist_id").bindTo { it.artistId }.references(ArtistsTable) { it.artist }
    val artId = varchar("art_id").bindTo { it.artId }
}