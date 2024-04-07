@file:Suppress("PropertyName")

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val toothpick_version: String by project
val ktorm_version: String by project
val hikaricp_version: String by project
val postgres_version: String by project
val dotenv_version: String by project
val flyway_version: String by project


plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("kapt") version "1.9.23"
    id("io.ktor.plugin") version "2.3.9"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
    id("org.flywaydb.flyway") version "9.20.0"
}

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.postgresql:postgresql:42.7.3")
    }
}

group = "me.abhigya"
version = "0.0.1"

kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
        kotlin.srcDir("build/generated/sources/i18n")
    }
    sourceSets.test {
        kotlin.srcDir("build/generated/ksp/test/kotlin")
    }
}

application {
    mainClass.set("me.abhigya.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime-jvm:0.6.0-RC.2")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-default-headers-jvm")
    implementation("io.ktor:ktor-server-cors-jvm")
    implementation("io.ktor:ktor-server-compression-jvm")
    implementation("io.ktor:ktor-server-resources")
    implementation("io.ktor:ktor-server-jetty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.github.cdimascio:dotenv-kotlin:$dotenv_version")

    // Database
    implementation("org.flywaydb:flyway-core:$flyway_version")
    implementation("org.ktorm:ktorm-core:$ktorm_version")
    implementation("org.ktorm:ktorm-support-postgresql:$ktorm_version")
    implementation("com.zaxxer:HikariCP:$hikaricp_version")
    implementation("org.postgresql:postgresql:$postgres_version")

    // DI
    implementation("com.github.stephanenicolas.toothpick:ktp:$toothpick_version")
    kapt("com.github.stephanenicolas.toothpick:toothpick-compiler:$toothpick_version")

    // Testing
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

flyway {
    driver = "org.postgresql.Driver"
    user = properties["user"].toString()
    password = properties["password"].toString()
    url = "jdbc:postgresql://${properties["host"]}:${properties["port"]}/${properties["database"]}?defaultRowFetchSize=1000&socketTimeout=30000&preparedStatementCacheQueries=25"
    validateMigrationNaming = true
    cleanOnValidationError = true
    table = "schema_history"
    locations = arrayOf("filesystem:src/main/resources/db/migrations")
}

tasks {
    shadowJar {
        mergeServiceFiles()
    }
}
