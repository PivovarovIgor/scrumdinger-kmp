package ru.brauer.scrumdinger

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform