package nalgoticas.salle.cinetrack.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import nalgoticas.salle.cinetrack.ui.discover.DiscoverScreen
import nalgoticas.salle.cinetrack.ui.home.DiaryScreen
import nalgoticas.salle.cinetrack.ui.home.HomeScreen
import nalgoticas.salle.cinetrack.ui.home.MovieDetailScreen
import nalgoticas.salle.cinetrack.ui.home.ProfileScreen
import nalgoticas.salle.cinetrack.ui.theme.CineTrackBottomBar

@Composable
fun CineTrackApp() {
    val navController = rememberNavController()
    val bg = Color(0xFF050510)

    Scaffold(
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

            // -------- HOME ----------
            composable("home") {
                HomeScreen(
                    onMovieClick = { movie ->
                        navController.navigate("details/${movie.id}")
                    }
                )
            }

            // -------- DISCOVER ----------
            composable("discover") {
                DiscoverScreen(
                    onMovieClick = { movie ->
                        navController.navigate("details/${movie.id}")
                    }
                )
            }

            // -------- DIARY / PROFILE ----------
            composable("diary")   { DiaryScreen() }
            composable("profile") { ProfileScreen() }

            // -------- DETALLE ----------
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
