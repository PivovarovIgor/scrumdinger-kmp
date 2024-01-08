package ru.brauer.scrumdinger.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseInExpo
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.EaseOutExpo
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
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
                    NavHost(
                        navController = navController,
                        startDestination = "scrums",
                        enterTransition = {
                            scaleIn(
                                animationSpec = tween(300, easing = EaseOutExpo),
                                transformOrigin = TransformOrigin(0.1f, 0.5f),
                                initialScale = 0.8f
                            ) + slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.End,
                                animationSpec = tween(300, easing = EaseOutExpo),
                                initialOffset = { it / 2 }
                            )
                        },
                        exitTransition = {
                            scaleOut(
                                animationSpec = tween(300, easing = EaseInExpo),
                                transformOrigin = TransformOrigin(0.1f, 0.5f),
                                targetScale = 0.8f
                            ) + slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                                animationSpec = tween(300, easing = EaseInExpo),
                                targetOffset = { it / 2 }
                            )
                        }
                    ) {
                        composable("scrums") {
                            ScrumView(
                                scrum.value,
                                onClick = {
                                    val route = SCRUM_DETAILS_ROUTE.replace(
                                        SCRUM_DETAILS_PATTERN_ARG,
                                        it.id
                                    )
                                    navController.navigate(route) { launchSingleTop = true }
                                })
                        }
                        composable(
                            route = SCRUM_DETAILS_ROUTE,
                            arguments = listOf(
                                navArgument(SCRUM_DETAILS_KEY) { type = NavType.StringType }
                            ),
                            enterTransition = {
                                slideIntoContainer(
                                    animationSpec = tween(300, easing = EaseIn),
                                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    animationSpec = tween(300, easing = EaseOut),
                                    towards = AnimatedContentTransitionScope.SlideDirection.End
                                )
                            }
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
