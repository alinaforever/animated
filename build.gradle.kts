plugins {
    kotlin("jvm") version "1.9.23"
}

group = "com.github.alinaforever"
version = "1.0.0"

repositories {
    mavenCentral()

    maven("https://raw.githubusercontent.com/kotlin-graphics/mary/master")
}

dependencies {
    testImplementation("kotlin.graphics:uno:0.7.21")
    testImplementation(platform("org.lwjgl:lwjgl-bom:3.3.3"))

    testImplementation("org.lwjgl", "lwjgl")
    testImplementation("org.lwjgl", "lwjgl-glfw")
    testImplementation("org.lwjgl", "lwjgl-nanovg")
    testImplementation("org.lwjgl", "lwjgl-opengl")
    testRuntimeOnly("org.lwjgl", "lwjgl", classifier = "natives-windows")
    testRuntimeOnly("org.lwjgl", "lwjgl-glfw", classifier = "natives-windows")
    testRuntimeOnly("org.lwjgl", "lwjgl-nanovg", classifier = "natives-windows")
    testRuntimeOnly("org.lwjgl", "lwjgl-opengl", classifier = "natives-windows")
}

kotlin {
    jvmToolchain(17)
}