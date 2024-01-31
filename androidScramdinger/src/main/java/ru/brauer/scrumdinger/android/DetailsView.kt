package ru.brauer.scrumdinger.android

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ru.brauer.scrumdinger.android.baseview.Label
import ru.brauer.scrumdinger.android.extensions.color
import ru.brauer.scrumdinger.models.DailyScrum
import ru.brauer.scrumdinger.models.viewName
import ru.brauer.scrumdinger.models.accentColor
import ru.brauer.scrumdinger.models.sampleScrum
import sections.InnerColors
import sections.Section
import sections.SectionLabel
import sections.SectionRow
import sections.labelStyle

@Composable
fun DetailsView(scrumId: String, navController: NavHostController, viewModel: ScrumViewModel) {
    Surface(color = MaterialTheme.colorScheme.background) {
        AppBar(scrumId, navController, viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(scrumId: String, navController: NavHostController, viewModel: ScrumViewModel) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    var isShowEditSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val scrum = viewModel.getScrumByIdFlow(scrumId)
        .collectAsStateWithLifecycle(initialValue = DailyScrum.empty)
    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        var isEnabled by remember { mutableStateOf(true) }
        LargeTopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ), title = {
            Text(
                text = scrum.value.title, maxLines = 1, overflow = TextOverflow.Ellipsis
            )
        }, navigationIcon = {
            IconButton(
                onClick = {
                    isEnabled = false
                    navController.popBackStack()
                }, enabled = isEnabled
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIos,
                    contentDescription = null,
                    tint = InnerColors.Blue
                )
            }
        }, actions = {
            IconButton(onClick = { isShowEditSheet = true }) {
                Text(text = "Edit", color = InnerColors.Blue)
            }
        }, scrollBehavior = scrollBehavior
        )
    }) { innerPadding ->
        DetailsViewScrollContent(scrum.value, innerPadding)
        if (isShowEditSheet) {
            ModalBottomSheet(
                onDismissRequest = { isShowEditSheet = false },
                sheetState = sheetState
            ) {
                EditSheet(
                    scrum = scrum.value,
                    sheetState = sheetState,
                    onChange = {
                        viewModel.update(it)
                        scope.launch { sheetState.hide() }
                            .invokeOnCompletion { isShowEditSheet = false }
                    },
                    onDismiss = {
                        scope.launch { sheetState.hide() }
                            .invokeOnCompletion { isShowEditSheet = false }
                    }
                )
            }
        }
    }
}

@Composable
fun DetailsViewScrollContent(scrum: DailyScrum, innerPadding: PaddingValues) {
    val stateScroll = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .verticalScroll(stateScroll),
    ) {
        Section(
            innerPadding = innerPadding, headerTitle = "Meeting info", itemsContents = listOf({
                SectionRow {
                    Label(
                        imageVector = Icons.Outlined.Timer,
                        text = "Start meeting",
                        style = labelStyle,
                        tint = InnerColors.Blue,
                        textColor = InnerColors.Blue
                    )
                    Icon(
                        imageVector = Icons.Outlined.ArrowForwardIos,
                        contentDescription = null,
                        tint = Color.LightGray
                    )
                }
            }, {
                SectionRow {
                    Label(
                        imageVector = Icons.Outlined.AccessTime,
                        text = "Length",
                        style = labelStyle,
                        tint = InnerColors.Blue
                    )
                    Text(
                        text = "${scrum.lengthInMinutes} minutes", style = labelStyle
                    )
                }
            }, {
                SectionRow {
                    Label(
                        imageVector = Icons.Outlined.Palette,
                        text = "Theme",
                        style = labelStyle,
                        tint = InnerColors.Blue
                    )
                    Surface(color = scrum.theme.color, shape = RoundedCornerShape(4.dp)) {
                        Text(modifier = Modifier.padding(4.dp),
                            color = scrum.theme.accentColor().color,
                            text = scrum.theme.viewName
                        )
                    }
                }
            })
        )

        Section(
            headerTitle = "Attendees",
            itemsContents = scrum.attendees.map { attendee ->
                {
                    SectionLabel(
                        imageVector = Icons.Outlined.Person,
                        text = attendee.name
                    )
                }
            })
    }
}


@Preview
@Composable
fun DetailsViewPreview() {
    val navController = rememberNavController()
    val viewModel: ScrumViewModel = ScrumViewModel()
    MyApplicationTheme {
        DetailsView(DailyScrum.sampleScrum.id, navController, viewModel)
    }
}