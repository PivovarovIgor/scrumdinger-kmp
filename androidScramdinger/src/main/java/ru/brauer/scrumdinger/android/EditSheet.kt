package ru.brauer.scrumdinger.android

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.brauer.scrumdinger.android.extensions.color
import ru.brauer.scrumdinger.models.DailyScrum
import ru.brauer.scrumdinger.models.sampleScrum
import sections.Section
import sections.SectionRow
import sections.labelStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditSheet(scrum: DailyScrum, onChange: (scrum: DailyScrum) -> Unit, onDismiss: () -> Unit) {
    var editableScrum by remember { mutableStateOf(scrum) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = { },
                navigationIcon = {
                    TextButton(onClick = { onDismiss.invoke() }) {
                        Text("Dismiss")
                    }
                },
                actions = {
                    TextButton(onClick = { onChange.invoke(editableScrum) }) {
                        Text(text = "Done")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        DetailEditView(editableScrum, innerPadding) {
            editableScrum = it
        }
    }
}

@Composable
fun DetailEditView(
    scrum: DailyScrum,
    innerPadding: PaddingValues,
    onChange: (scrum: DailyScrum) -> Unit
) {
    val stateScroll = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .verticalScroll(stateScroll)
    ) {
        Section(
            headerTitle = "Meeting info",
            innerPadding = innerPadding,
            itemsContents = listOf({
                SectionRow {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = scrum.title,
                        onValueChange = { onChange(scrum.copy(title = it)) },
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            }, {
                SectionRow {
                    Slider(
                        modifier = Modifier.fillMaxWidth(0.74f),
                        value = scrum.lengthInMinutes.toFloat(),
                        onValueChange = {
                            val newVal = it.toInt()
                            if (newVal != scrum.lengthInMinutes) {
                                onChange.invoke(scrum.copy(lengthInMinutes = newVal))
                            }
                        },
                        valueRange = 5f..30f,
                        steps = 25
                    )
                    Text(text = "${scrum.lengthInMinutes} minutes")
                }
            }, {
                SectionRow {
                    Text(text = "Theme")
                    Surface(
                        modifier = Modifier
                            .padding(vertical = 6.dp)
                            .height(26.dp)
                            .fillMaxWidth(0.84f),
                        color = scrum.theme.color,
                        shape = RoundedCornerShape(4.dp)
                    ) { }
                    Icon(
                        imageVector = Icons.Outlined.ArrowForwardIos,
                        contentDescription = null,
                        tint = Color.LightGray
                    )
                }
            })
        )
        Section(
            headerTitle = "Attendees",
            itemsContents = buildList {
                addAll(scrum.attendees.mapIndexed { index, attendee ->
                    {
                        SectionRow {
                            Text(
                                text = attendee.name,
                                style = labelStyle
                            )
                        }
                    }
                })
                var inputName by remember { mutableStateOf("") }
                var enabledAddButton by remember { mutableStateOf(false) }
                val addAction = {
                    onChange.invoke(
                        scrum.copy(
                            attendees = scrum.attendees + DailyScrum.Attendee(
                                inputName
                            )
                        )
                    )
                    inputName = ""
                    enabledAddButton = false
                }
                add {
                    SectionRow {
                        OutlinedTextField(
                            value = inputName,
                            onValueChange = {
                                inputName = it
                                enabledAddButton = it.isNotBlank()
                            },
                            keyboardActions = KeyboardActions(onDone = {
                                addAction.invoke()
                            })
                        )
                        IconButton(onClick = addAction, enabled = enabledAddButton) {
                            Icon(
                                imageVector = Icons.Filled.AddCircleOutline,
                                contentDescription = null
                            )
                        }
                    }
                }
            }

        )
    }
}

@Preview
@Composable
fun EditSheetPreview() {
    MyApplicationTheme {
        EditSheet(scrum = DailyScrum.sampleScrum, onChange = {}, onDismiss = {})
    }
}