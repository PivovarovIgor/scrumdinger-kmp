package ru.brauer.scrumdinger

import platform.Foundation.NSUUID.Companion.UUID


actual fun createId(): String = UUID().UUIDString()