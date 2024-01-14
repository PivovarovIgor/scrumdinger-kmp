package ru.brauer.scrumdinger.models

import ru.brauer.scrumdinger.createId

data class DailyScrum private constructor(
    val title: String,
    val attendees: List<Attendee>,
    val lengthInMinutes: Int,
    val theme: Theme,
    val id: String = createId()
) {
    constructor(
        title: String,
        attendees: List<Attendee>,
        lengthInMinutes: Int,
        theme: Theme
    ) : this(
            title = title,
            attendees = attendees,
            lengthInMinutes = lengthInMinutes,
            theme = theme,
            id = createId()
        )

    companion object {
        val empty: DailyScrum by lazy { DailyScrum("", emptyList(), 0, Theme.TAN, "") }
    }

    data class Attendee(
        val name: String
    )
}

val DailyScrum.Companion.sampleDaily: List<DailyScrum>
    get() = listOf(
        DailyScrum(
            title = "Design",
            attendees = listOf("Cathy", "Daisy", "Simon", "Jonathan").map(DailyScrum::Attendee),
            lengthInMinutes = 10,
            theme = Theme.YELLOW
        ),
        DailyScrum(
            title = "App Dev",
            attendees = listOf("Katie", "Gray", "Euna", "Luis", "Darla").map(DailyScrum::Attendee),
            lengthInMinutes = 5,
            theme = Theme.ORANGE
        ),
        DailyScrum(
            title = "Web Dev",
            attendees = listOf(
                "Chella",
                "Chris",
                "Christina",
                "Eden",
                "Karla",
                "Lindsey",
                "Aga",
                "Chad",
                "Jenn",
                "Sarah"
            )
                .map(DailyScrum::Attendee),
            lengthInMinutes = 5,
            theme = Theme.INDIGO
        )
    )

val DailyScrum.Companion.sampleScrum: DailyScrum get() = sampleDaily.first()