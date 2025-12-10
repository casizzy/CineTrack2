package nalgoticas.salle.cinetrack.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class RecentActivity(
    val movieTitle: String,
    val time: String,
    val rating: Int
)

private val sampleActivity = listOf(
    RecentActivity(
        movieTitle = "The Dark Knight",
        time = "2 days ago",
        rating = 5
    ),
    RecentActivity(
        movieTitle = "Parasite",
        time = "1 week ago",
        rating = 4
    )
)

data class StatItem(
    val icon: ImageVector,
    val label: String,
    val count: String,
    val gradient: List<Color>
)

private val statsItems = listOf(
    StatItem(
        icon = Icons.Filled.Movie,
        label = "Films Watched",
        count = "4",
        gradient = listOf(Color(0xFFFF8A3C), Color(0xFFFF4F6A))
    ),
    StatItem(
        icon = Icons.Filled.Star,
        label = "Ratings",
        count = "4",
        gradient = listOf(Color(0xFFFFC045), Color(0xFFFFA726))
    ),
    StatItem(
        icon = Icons.Filled.CalendarMonth,
        label = "Reviews",
        count = "1",
        gradient = listOf(Color(0xFF4FC3F7), Color(0xFF1976D2))
    ),
    StatItem(
        icon = Icons.Filled.Favorite,
        label = "Favorites",
        count = "2",
        gradient = listOf(Color(0xFFFF4F6A), Color(0xFFE91E63))
    )
)


@Composable
fun ProfileScreen() {
    val bg = Color(0xFF050510)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 16.dp)
    ) {

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Profile",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(Modifier.height(16.dp))

        UserCard(
            name = "Joe Mama",
            username = "@joemama"
        )

        Spacer(Modifier.height(24.dp))

        StatsGrid()

        Spacer(Modifier.height(32.dp))

        Text(
            text = "Recent Activity",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )

        Spacer(Modifier.height(14.dp))

        sampleActivity.forEach { activity ->
            RecentActivityCard(activity)
            Spacer(Modifier.height(14.dp))
        }
    }
}


@Composable
private fun UserCard(name: String, username: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(26.dp))
            .background(Color(0xFF10101A))
            .padding(horizontal = 18.dp, vertical = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(82.dp)
                        .clip(CircleShape)
                        .drawBehind {
                            drawRoundRect(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFFFF8A3C),
                                        Color(0xFFFF4F6A)
                                    )
                                ),
                                cornerRadius = CornerRadius(46.dp.toPx())
                            )
                        }
                        .padding(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(Color(0xFF2B2B38)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "J",
                            color = Color.White,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(Modifier.width(16.dp))

                Column {
                    Text(
                        text = name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Text(
                        text = username,
                        fontSize = 14.sp,
                        color = Color(0xFFB0B0C0)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF11111C))
                    .border(
                        width = 1.5.dp,
                        brush = Brush.linearGradient(
                            listOf(Color(0xFFFF8A3C), Color(0xFFFF4F6A))
                        ),
                        shape = RoundedCornerShape(14.dp)
                    )
                    .clickable {
                        // logica de logout
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Logout,
                    contentDescription = "Logout",
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}


@Composable
private fun StatsGrid() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatCard(
                item = statsItems[0],
                modifier = Modifier.weight(1f)
            )
            StatCard(
                item = statsItems[1],
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatCard(
                item = statsItems[2],
                modifier = Modifier.weight(1f)
            )
            StatCard(
                item = statsItems[3],
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun StatCard(
    item: StatItem,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(130.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(Color(0xFF050510))
            .border(
                width = 1.dp,
                color = Color(0x33FFFFFF),
                shape = RoundedCornerShape(22.dp)
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF11111C))
                    .border(
                        width = 1.5.dp,
                        brush = Brush.linearGradient(item.gradient),
                        shape = RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = item.gradient.last(),
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = item.count,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(Modifier.height(2.dp))

            Text(
                text = item.label,
                fontSize = 13.sp,
                color = Color(0xFF8A8A99)
            )
        }
    }
}


@Composable
private fun RecentActivityCard(activity: RecentActivity) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(Color(0xFF10101A))
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFF11111C))
                        .border(
                            width = 1.5.dp,
                            brush = Brush.linearGradient(
                                listOf(
                                    Color(0xFF4FC3F7),
                                    Color(0xFF1976D2)
                                )
                            ),
                            shape = RoundedCornerShape(14.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC045),
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Reviewed ",
                            fontSize = 14.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = activity.movieTitle,
                            fontSize = 14.sp,
                            color = Color(0xFFFF8A3C),
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(Modifier.weight(1f))

                        Text(
                            text = activity.time,
                            fontSize = 12.sp,
                            color = Color(0xFF8A8A99)
                        )
                    }
                }
            }

            Spacer(Modifier.height(10.dp))

            Row {
                repeat(activity.rating) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC045),
                        modifier = Modifier.size(15.dp)
                    )
                }
            }
        }
    }
}
