package nalgoticas.salle.cinetrack.ui.screens.home.diaryScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nalgoticas.salle.cinetrack.data.MovieCollections
import nalgoticas.salle.cinetrack.models.Pelicula
import nalgoticas.salle.cinetrack.services.MovieService
import nalgoticas.salle.cinetrack.ui.screens.home.components.MovieGrid
import nalgoticas.salle.cinetrack.ui.theme.background
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private enum class DiaryFilter(val label: String) {
    Watched("Watched"),
    Favorites("Favorites")
}

@Composable
fun DiaryScreen() {
    val bg = background
    var movies by remember { mutableStateOf<List<Pelicula>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var currentFilter by remember { mutableStateOf(DiaryFilter.Watched) }


    LaunchedEffect(Unit) {
        isLoading = true
        error = null
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api-app-peliculas.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(MovieService::class.java)
            val result = withContext(Dispatchers.IO) { service.getMovies() }
            movies = result
        } catch (e: Exception) {
            Log.e("DiaryScreen", "Error: ${e.message}", e)
            error = "Error loading movies"
        } finally {
            isLoading = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
    ) {
        when {
            isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFFFF4F6A))
                }
            }

            error != null -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(error ?: "Error", color = Color.Red)
                }
            }

            else -> {
                val watchedMovies = movies.filter { MovieCollections.isWatched(it.id) }
                val favoriteMovies = movies.filter { MovieCollections.isFavorite(it.id) }

                val moviesToShow = when (currentFilter) {
                    DiaryFilter.Watched -> watchedMovies
                    DiaryFilter.Favorites -> favoriteMovies
                }

                val watchedCount = watchedMovies.size
                val favoritesCount = favoriteMovies.size

                Column(
                    modifier = Modifier.fillMaxSize()
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
                            fontSize = 22.sp
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

                    MovieGrid(
                        movies = moviesToShow,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        enableActions = true,
                        onMovieClick = { /* opcional navegar a detail desde diary */ }
                    )
                }
            }
        }
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
            .clip(androidx.compose.foundation.shape.RoundedCornerShape(24.dp))
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
                        .clip(androidx.compose.foundation.shape.RoundedCornerShape(20.dp))
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
