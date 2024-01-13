package ru.brauer.scrumdinger.android

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.brauer.scrumdinger.android.baseview.Label
import ru.brauer.scrumdinger.android.extensions.color
import ru.brauer.scrumdinger.models.DailyScrum
import ru.brauer.scrumdinger.models.accentColor
import ru.brauer.scrumdinger.models.sampleScrum
import java.util.Locale

@Composable
fun DetailsView(scrum: DailyScrum, navController: NavHostController) {
    Surface(color = MaterialTheme.colorScheme.background) {
        AppBar(scrum, navController)
    }
}

private val labelStyle
    @Composable
    get() = MaterialTheme.typography.headlineSmall

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(scrum: DailyScrum, navController: NavHostController) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            var isEnabled by remember { mutableStateOf(true) }
            LargeTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Text(
                        text = scrum.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            isEnabled = false
                            navController.popBackStack()
                        },
                        enabled = isEnabled
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIos,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = null
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        DetailsViewScrollContent(scrum, innerPadding)
    }
}

@Composable
fun DetailsViewScrollContent(scrum: DailyScrum, innerPadding: PaddingValues) {
    val stateScroll = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(stateScroll),
    ) {
        SectionHeader(
            modifier = Modifier.padding(innerPadding),
            text = "Meeting info"
        )
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

@Composable
private fun SectionHeader(modifier: Modifier = Modifier, text: String) {
    Spacer(modifier = modifier.height(30.dp))
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
    val navController = rememberNavController()
    MyApplicationTheme {
        DetailsView(DailyScrum.sampleScrum, navController)
    }
}