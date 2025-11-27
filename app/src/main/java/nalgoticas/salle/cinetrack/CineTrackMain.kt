package nalgoticas.salle.cinetrack.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.padding
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nalgoticas.salle.cinetrack.ui.discover.DiscoverScreen
import nalgoticas.salle.cinetrack.ui.home.HomeScreen
import nalgoticas.salle.cinetrack.ui.theme.CineTrackBottomBar
import nalgoticas.salle.cinetrack.ui.home.DiaryScreen
import nalgoticas.salle.cinetrack.ui.home.ProfileScreen

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
            composable("home")     { HomeScreen() }
            composable("discover") { DiscoverScreen() }
            composable("diary")    { DiaryScreen() }
            composable("profile")  { ProfileScreen() }
        }

    }
}


@Composable
fun DiaryScreen() {
    SimpleCenterText("Diary")
}

@Composable
fun ProfileScreen() {
    SimpleCenterText("Profile")
}

@Composable
private fun SimpleCenterText(text: String) {
    androidx.compose.foundation.layout.Box(
        modifier = androidx.compose.ui.Modifier
            .padding(16.dp)
            .fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        androidx.compose.material3.Text(
            text = text,
            color = Color.White
        )
    }
}
