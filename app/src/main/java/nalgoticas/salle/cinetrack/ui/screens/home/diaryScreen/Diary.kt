package nalgoticas.salle.cinetrack.ui.screens.home.diaryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import nalgoticas.salle.cinetrack.data.Movie
import nalgoticas.salle.cinetrack.data.MovieCollections
import nalgoticas.salle.cinetrack.data.MovieData
import nalgoticas.salle.cinetrack.ui.screens.home.diaryScreen.components.MovieDiaryCard

private val allMovies = MovieData.movies

private enum class DiaryFilter(val label: String) {
    Watched("Watched"),
    Favorites("Favorites")
}

@Composable
fun DiaryScreen() {
    val bg = Color(0xFF050510)
    var currentFilter by remember { mutableStateOf(DiaryFilter.Watched) }

    val watchedMovies = allMovies.filter { MovieCollections.isWatched(it.id) }
    val favoriteMovies = allMovies.filter { MovieCollections.isFavorite(it.id) }

    val moviesToShow = when (currentFilter) {
        DiaryFilter.Watched -> watchedMovies
        DiaryFilter.Favorites -> favoriteMovies
    }

    val watchedCount = watchedMovies.size
    val favoritesCount = favoriteMovies.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(bg)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "My Diary",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            DiaryTabs(
                current = currentFilter,
                watchedCount = watchedCount,
                favoritesCount = favoritesCount,
                onChange = { currentFilter = it }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0x33FFFFFF))
        )

        Spacer(Modifier.height(12.dp))

        MovieDiaryGrid(
            movies = moviesToShow,
            isWatched = { movie -> MovieCollections.isWatched(movie.id) },
            isFavorite = { movie -> MovieCollections.isFavorite(movie.id) },
            onToggleWatched = { movie -> MovieCollections.toggleWatched(movie.id) },
            onToggleFavorite = { movie -> MovieCollections.toggleFavorite(movie.id) }
        )
    }
}

@Composable
private fun DiaryTabs(
    current: DiaryFilter,
    watchedCount: Int,
    favoritesCount: Int,
    onChange: (DiaryFilter) -> Unit
) {
    val glass = Color(0xCC151521)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(glass)
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DiaryFilter.values().forEach { filter ->
                val selected = filter == current
                val gradient = Brush.horizontalGradient(
                    listOf(Color(0xFFFF8A3C), Color(0xFFFF4F6A))
                )

                val label = when (filter) {
                    DiaryFilter.Watched -> "Watched ($watchedCount)"
                    DiaryFilter.Favorites -> "Favorites ($favoritesCount)"
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .then(
                            if (selected) {
                                Modifier.drawBehind {
                                    drawRoundRect(
                                        brush = gradient,
                                        cornerRadius = CornerRadius(20.dp.toPx())
                                    )
                                }
                            } else Modifier
                        )
                        .clickable { onChange(filter) }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        color = if (selected) Color.White else Color(0xFF8A8A99),
                        fontSize = 13.sp,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Composable
private fun MovieDiaryGrid(
    movies: List<Movie>,
    isWatched: (Movie) -> Boolean,
    isFavorite: (Movie) -> Boolean,
    onToggleWatched: (Movie) -> Unit,
    onToggleFavorite: (Movie) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(bottom = 80.dp, top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(movies, key = { it.id }) { movie ->
            MovieDiaryCard(
                movie = movie,
                watched = isWatched(movie),
                favorite = isFavorite(movie),
                onToggleWatched = { onToggleWatched(movie) },
                onToggleFavorite = { onToggleFavorite(movie) }
            )
        }
    }
}
