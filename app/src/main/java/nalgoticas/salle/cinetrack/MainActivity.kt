package nalgoticas.salle.cinetrack

import CineTrackApp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import nalgoticas.salle.cinetrack.ui.screens.home.components.CineTrackBottomBar
import nalgoticas.salle.cinetrack.ui.screens.home.diaryScreen.DiaryScreen
import nalgoticas.salle.cinetrack.ui.screens.home.DiscoverScreen
import nalgoticas.salle.cinetrack.ui.screens.home.HomeScreen
import nalgoticas.salle.cinetrack.ui.screens.home.MovieDetailScreen
import nalgoticas.salle.cinetrack.ui.screens.home.ProfileScreen
import nalgoticas.salle.cinetrack.ui.theme.CineTrackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CineTrackTheme {
                val navController = rememberNavController()
                val bg = Color(0xFF050510)


                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = bg,
                    bottomBar = {
                        CineTrackBottomBar(navController = navController)
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        composable("home") {
                            HomeScreen(
                                onMovieClick = { movie ->
                                    navController.navigate("details/${movie.id}")
                                }
                            )
                        }

                        composable("discover") {
                            DiscoverScreen(
                                onMovieClick = { movie ->
                                    navController.navigate("details/${movie.id}")
                                }
                            )
                        }

                        composable("diary")   { DiaryScreen() }
                        composable("profile") { ProfileScreen() }

                        composable(
                            route = "details/{movieId}",
                            arguments = listOf(
                                navArgument("movieId") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getInt("movieId") ?: return@composable
                            MovieDetailScreen(
                                movieId = id,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CineTrackPreview() {
    CineTrackTheme {
        CineTrackApp()
    }
}