package ru.brauer.scrumdinger.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.brauer.scrumdinger.models.DailyScrum
import ru.brauer.scrumdinger.models.sampleDaily
import java.util.UUID

class MainActivity : ComponentActivity() {

    private val viewModel: ScrumViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val scrum = viewModel.scrum.collectAsStateWithLifecycle()
                    ScrumView(scrum.value)
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        ScrumView(DailyScrum.sampleDaily)
    }
}

class ScrumViewModel : ViewModel() {
    private val _scrums: MutableStateFlow<List<DailyScrum>> = MutableStateFlow(DailyScrum.sampleDaily)
    val scrum = _scrums.asStateFlow()
}
