package ru.brauer.scrumdinger.android

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.brauer.scrumdinger.models.DailyScrum
import ru.brauer.scrumdinger.models.sampleDaily

@Composable
fun ScrumView(scrums: List<DailyScrum>, onClick: (scrum: DailyScrum) -> Unit) {
    LazyColumn(Modifier.padding(12.dp)) {
        items(items = scrums) {
            CardView(scrum = it, onClick)
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}

@Preview
@Composable
fun ScumViewPreview() {
    MyApplicationTheme {
        ScrumView(DailyScrum.sampleDaily) {}
    }
}