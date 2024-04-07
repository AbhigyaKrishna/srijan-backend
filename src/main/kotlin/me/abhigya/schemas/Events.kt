package me.abhigya.schemas

import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import org.ktorm.entity.Entity
import org.ktorm.schema.*

interface Events : Entity<Events> {
    companion object : Entity.Factory<Events>()

    val id: Int
    var name: String
    var description: String
    var fromDate: Instant
    var toDate: Instant
    var venue: String
    var highlight: Boolean
}

interface EventsArt : Entity<EventsArt> {
    companion object : Entity.Factory<EventsArt>()

    var eventId: Int
    val event: Events
    var artId: String
}

object EventsTable : Table<Events>("events") {
    val id = int("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val description = varchar("description").bindTo { it.description }
    val fromDate = timestamp("from_date").transform({ it.toKotlinInstant() }, { it.toJavaInstant() }).bindTo { it.fromDate }
    val toDate = timestamp("to_date").transform({ it.toKotlinInstant() }, { it.toJavaInstant() }).bindTo { it.toDate }
    val venue = varchar("venue").bindTo { it.venue }
    val highlight = boolean("highlight").bindTo { it.highlight }
}

object EventsArtTable : Table<EventsArt>("events_art") {
    val eventId = int("event_id").bindTo { it.eventId }.references(EventsTable) { it.event }
    val artId = varchar("art_id").bindTo { it.artId }
}
