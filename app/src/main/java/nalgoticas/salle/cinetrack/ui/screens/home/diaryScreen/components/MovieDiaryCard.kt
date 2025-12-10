package nalgoticas.salle.cinetrack.ui.screens.home.diaryScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import nalgoticas.salle.cinetrack.models.Pelicula

@Composable
fun MovieDiaryCard(
    movie: Pelicula,
    watched: Boolean,
    favorite: Boolean,
    onToggleWatched: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    var showActions by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF262636))
                .clickable { showActions = !showActions }
        ) {
            AsyncImage(
                model = movie.image_url,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0xCC000000)
                            )
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopStart)
                    .clip(RoundedCornerShape(50))
                    .background(
                        if (watched) Color(0xFF1AC98A)
                        else Color(0x661AC98A)
                    )
            ) {
                Icon(
                    imageVector = Icons.Filled.Visibility,
                    contentDescription = "Watched",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(6.dp)
                        .size(16.dp)
                )
            }

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(50))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFFFC045),
                                Color(0xFFFF8A3C)
                            )
                        )
                    )
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Rating",
                        tint = Color.White,
                        modifier = Modifier
                            .size(14.dp)
                            .padding(end = 2.dp)
                    )
                    Text(
                        text = movie.rating.toString(),
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            if (showActions) {
                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircleActionButton(
                        icon = Icons.Filled.Visibility,
                        bg = Color(0xFF1AC98A),
                        active = watched,
                        onClick = onToggleWatched
                    )
                    CircleActionButton(
                        icon = Icons.Filled.Favorite,
                        bg = Color(0xFFFF4F6A),
                        active = favorite,
                        onClick = onToggleFavorite
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = movie.title,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(Modifier.height(2.dp))

        Text(
            text = "${movie.year}",
            color = Color(0xFF8A8A99),
            fontSize = 12.sp
        )

        Spacer(Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            repeat(5) { index ->
                val filled = index < movie.rating.toInt()
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = if (filled) Color(0xFFFFC045) else Color(0xFF3A3A4A),
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}