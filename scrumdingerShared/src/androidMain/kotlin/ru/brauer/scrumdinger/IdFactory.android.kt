package ru.brauer.scrumdinger

import java.util.UUID

actual fun createId(): String = UUID.randomUUID().toString()