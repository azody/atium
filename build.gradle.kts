plugins {
    application
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
    kotlin("plugin.serialization") version "2.1.0"
}

application {
    mainClass.set("azody.atium.MainKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    implementation("com.squareup.okhttp3:okhttp:4.9.3")

    implementation("io.github.cdimascio:dotenv-kotlin:6.4.0")

    implementation("org.knowm.xchart:xchart:3.8.7")

    testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
    testImplementation("io.kotest:kotest-assertions-core:5.9.1")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
