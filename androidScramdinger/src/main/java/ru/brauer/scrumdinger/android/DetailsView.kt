package ru.brauer.scrumdinger.android

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.brauer.scrumdinger.android.baseview.Label
import ru.brauer.scrumdinger.android.extensions.color
import ru.brauer.scrumdinger.models.DailyScrum
import ru.brauer.scrumdinger.models.accentColor
import ru.brauer.scrumdinger.models.sampleScrum
import java.util.Locale

@Composable
fun DetailsView(scrum: DailyScrum) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = scrum.title,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            SectionHeader(text = "Meeting info")
            Surface(shape = RoundedCornerShape(10.dp)) {
                Column {
                    Label(
                        imageVector = Icons.Outlined.Timer,
                        text = "Start meeting",
                        style = labelStyle
                    )
                    Divider()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Label(
                            imageVector = Icons.Outlined.AccessTime,
                            text = "Length",
                            style = labelStyle
                        )
                        Text(
                            text = "${scrum.lengthInMinutes} minutes",
                            style = labelStyle
                        )
                    }
                    Divider()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Label(
                            imageVector = Icons.Outlined.Palette,
                            text = "Theme",
                            style = labelStyle
                        )
                        Surface(color = scrum.theme.color, shape = RoundedCornerShape(4.dp)) {
                            Text(
                                modifier = Modifier.padding(4.dp),
                                color = scrum.theme.accentColor().color,
                                text = scrum.theme.name
                                    .lowercase(Locale.getDefault())
                                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
                        }
                    }
                }
            }

            SectionHeader(text = "Attendees")
            Surface(shape = RoundedCornerShape(10.dp)) {
                Column {
                    scrum.attendees.forEachIndexed { index, attendee ->
                        Label(
                            imageVector = Icons.Outlined.Person,
                            text = attendee.name,
                            style = labelStyle
                        )
                        if (index < scrum.attendees.lastIndex) {
                            Divider()
                        }
                    }
                }
            }
        }
    }
}

private val labelStyle
    @Composable
    get() = MaterialTheme.typography.headlineSmall

@Composable
private fun SectionHeader(text: String) {
    Spacer(modifier = Modifier.height(30.dp))
    Text(
        modifier = Modifier.padding(start = 16.dp),
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = Color.Gray
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview
@Composable
fun DetailsViewPreview() {
    MyApplicationTheme {
        DetailsView(DailyScrum.sampleScrum)
    }
}