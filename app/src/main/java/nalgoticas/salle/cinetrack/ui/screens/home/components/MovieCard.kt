package nalgoticas.salle.cinetrack.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import nalgoticas.salle.cinetrack.data.MovieCollections.isWatched
import nalgoticas.salle.cinetrack.models.Pelicula
import nalgoticas.salle.cinetrack.ui.screens.home.diaryScreen.components.CircleActionButton


@Composable
fun MovieCard(
    movie: Pelicula,
    modifier: Modifier = Modifier,
    watched: Boolean? = null,
    favorite: Boolean? = null,
    enableActions: Boolean = false,
    onClick: (() -> Unit)? = null,
    onToggleWatched: (() -> Unit)? = null,
    onToggleFavorite: (() -> Unit)? = null
) {
    var showActions by remember { mutableStateOf(false) }
    val isWatched = watched == true

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick?.invoke()
                if (enableActions) showActions = !showActions
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF262636))
        ) {
            // imagen
            AsyncImage(
                model = movie.image_url,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // overlay
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color(0xCC000000))
                        )
                    )
            )

            // badge watched
            if (watched != null) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopStart)
                        .clip(RoundedCornerShape(50))
                        .background(
                            color = if (watched) Color(0xFF1AC98A) else Color(0x661AC98A),
                            shape = RoundedCornerShape(50)
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
            }


            // badge rating
            RatingBadge(
                rating = movie.rating,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            )


            // acciones (solo en diary si enableActions = true)
            if (enableActions && showActions) {
                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    onToggleWatched?.let {
                        CircleActionButton(
                            icon = Icons.Filled.Visibility,
                            bg = Color(0xFF1AC98A),
                            active = watched == true,
                            onClick = it
                        )
                    }
                    onToggleFavorite?.let {
                        CircleActionButton(
                            icon = Icons.Filled.Favorite,
                            bg = Color(0xFFFF4F6A),
                            active = favorite == true,
                            onClick = it
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = movie.title,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp
        )

        Text(
            text = movie.year.toString(),
            color = Color(0xFF8A8A99),
            fontSize = 12.sp
        )

        RatingStars(rating = movie.rating)
    }
}
