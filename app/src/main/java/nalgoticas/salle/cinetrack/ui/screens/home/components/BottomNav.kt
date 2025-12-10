package nalgoticas.salle.cinetrack.ui.screens.home.components
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import nalgoticas.salle.cinetrack.ui.theme.background


sealed class BottomDest(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Home     : BottomDest("home", "Home", Icons.Filled.Home)
    object Discover : BottomDest("discover", "Discover", Icons.Filled.Search)
    object Diary    : BottomDest("diary", "Diary", Icons.Filled.List)
    object Profile  : BottomDest("profile", "Profile", Icons.Filled.Person)
}

private val bottomItems = listOf(
    BottomDest.Home,
    BottomDest.Discover,
    BottomDest.Diary,
    BottomDest.Profile
)


@Composable
fun CineTrackBottomBar(
    navController: NavController
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(background)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomItems.forEach { item ->
                val selected = item.route == currentRoute

                val gradient = Brush.horizontalGradient(
                    listOf(
                        Color(0xFFFF9A3C),
                        Color(0xFFFF4F45)
                    )
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .clip(RoundedCornerShape(24.dp))

                        .then(
                            if (selected) {
                                Modifier.drawBehind {
                                    drawRoundRect(
                                        brush = gradient,
                                        cornerRadius = CornerRadius(24.dp.toPx())
                                    )
                                }
                            } else {
                                Modifier
                            }
                        )
                        .clickable {
                            if (!selected) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                        .padding(
                            vertical = 6.dp,
                            horizontal = if (selected) 10.dp else 0.dp
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (selected) Color.White else Color(0xFF8A8A99),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = item.label,
                            color = if (selected) Color.White else Color(0xFF8A8A99),
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}