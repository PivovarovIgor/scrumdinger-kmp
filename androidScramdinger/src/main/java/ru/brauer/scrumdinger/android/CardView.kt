package ru.brauer.scrumdinger.android

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.elevatedCardElevation
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.brauer.scrumdinger.android.baseview.Label
import ru.brauer.scrumdinger.android.extensions.color
import ru.brauer.scrumdinger.models.DailyScrum
import ru.brauer.scrumdinger.models.accentColor
import ru.brauer.scrumdinger.models.sampleScrum

@Composable
fun CardView(scrum: DailyScrum) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = scrum.theme.color,
            contentColor = scrum.theme.accentColor().color
        ),
        shape = RoundedCornerShape(10.dp),
        elevation = elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(all = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                scrum.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Label(
                    imageVector = Icons.Outlined.Groups,
                    text = scrum.attendees.count().toString()
                )
                Label(
                    imageVector = Icons.Outlined.WatchLater,
                    text = scrum.lengthInMinutes.toString(),
                    trailingIcon = true
                )
            }
        }
    }
}

@Preview
@Composable
fun CardVPreview() {
    MyApplicationTheme {
        CardView(DailyScrum.sampleScrum)
    }
}