package me.abhigya.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.application.*
import org.ktorm.database.Database
import org.ktorm.logging.ConsoleLogger
import org.ktorm.logging.LogLevel
import org.ktorm.logging.detectLoggerImplementation
import toothpick.ktp.KTP
import toothpick.ktp.binding.bind
import toothpick.ktp.binding.module
import kotlin.time.Duration.Companion.minutes

fun Application.configureDatabases(dbConf: DatabaseConfig, isDevelopment: Boolean = false) {
    val config = HikariConfig()
    config.driverClassName = "org.postgresql.Driver"
    config.username = dbConf.user
    config.password = dbConf.password
    config.jdbcUrl = "jdbc:postgresql://${dbConf.host}:${dbConf.port}/${dbConf.database}?defaultRowFetchSize=1000&socketTimeout=30000&preparedStatementCacheQueries=25"

    config.connectionTimeout = 30000
    config.maxLifetime = 25.minutes.inWholeMilliseconds
    config.minimumIdle = 6
    config.maximumPoolSize = 6
    config.isAutoCommit = false
    config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    config.poolName = "narwhal-postgres"
    config.connectionInitSql = "SET NAMES 'UTF8';"
    config.isIsolateInternalQueries = true

    val database = Database.connect(
        HikariDataSource(config),
        logger = if (isDevelopment) ConsoleLogger(threshold = LogLevel.DEBUG) else detectLoggerImplementation()
    )

    KTP.openRootScope().installModules(module {
        bind<Database>().toInstance(database)
    })
}

data class DatabaseConfig(
    val host: String,
    val port: Int,
    val database: String,
    val user: String,
    val password: String
) {
    companion object {
        fun fromEnv(dotenv: Dotenv = dotenv()): DatabaseConfig = DatabaseConfig(
            dotenv["DATABASE_HOSTNAME", "localhost"],
            dotenv["DATABASE_PORT"]?.toInt() ?: 5432,
            dotenv["DATABASE_DB", "postgres"],
            dotenv["DATABASE_USER", "postgres"],
            dotenv["DATABASE_PASSWORD", "postgres"]
        )
    }
}
