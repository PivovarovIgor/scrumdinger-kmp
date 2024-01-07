package ru.brauer.scrumdinger.android

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.brauer.scrumdinger.models.DailyScrum
import ru.brauer.scrumdinger.models.sampleScrum

@Composable
fun DetailsView(decodeFromString: DailyScrum) {
    Text(decodeFromString.toString())
}

@Preview
@Composable
fun DetailsViewPreview() {
    MyApplicationTheme {
        DetailsView(DailyScrum.sampleScrum)
    }
}