import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nalgoticas.salle.cinetrack.ui.theme.CineTrackBottomBar


import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController

import nalgoticas.salle.cinetrack.ui.screens.home.DiscoverScreen
import nalgoticas.salle.cinetrack.ui.screens.home.HomeScreen
import nalgoticas.salle.cinetrack.ui.screens.home.MovieDetailScreen
import nalgoticas.salle.cinetrack.ui.screens.home.ProfileScreen
import nalgoticas.salle.cinetrack.ui.screens.home.diaryScreen.DiaryScreen
import nalgoticas.salle.cinetrack.ui.theme.background

@Composable
fun CineTrackApp() {
    val navController = rememberNavController()
    val bg = background

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
