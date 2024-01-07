package ru.brauer.scrumdinger.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.brauer.scrumdinger.models.DailyScrum
import ru.brauer.scrumdinger.models.sampleDaily

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
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "scrums") {
                        composable("scrums") {
                            ScrumView(
                                scrum.value,
                                onClick = {
                                    val route = SCRUM_DETAILS_ROUTE.replace(
                                        SCRUM_DETAILS_PATTERN_ARG,
                                        it.id
                                    )
                                    navController.navigate(route)
                                })
                        }
                        composable(SCRUM_DETAILS_ROUTE,
                            arguments = listOf(
                                navArgument(SCRUM_DETAILS_KEY) { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val details = backStackEntry.arguments?.getString(SCRUM_DETAILS_KEY)
                                ?.let { scrumId ->
                                    viewModel.scrum.value.firstOrNull { it.id == scrumId }
                                }
                                ?: DailyScrum.empty
                            DetailsView(details)
                        }
                    }
                }
            }
        }
    }
}

private const val SCRUM_DETAILS_KEY = "scrumId"
private const val SCRUM_DETAILS_PATTERN_ARG = "{$SCRUM_DETAILS_KEY}"
private const val SCRUM_DETAILS_ROUTE = "scrum_details?scrumId=$SCRUM_DETAILS_PATTERN_ARG"

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        ScrumView(DailyScrum.sampleDaily) {}
    }
}

class ScrumViewModel : ViewModel() {
    private val _scrums: MutableStateFlow<List<DailyScrum>> =
        MutableStateFlow(DailyScrum.sampleDaily)
    val scrum = _scrums.asStateFlow()
}
