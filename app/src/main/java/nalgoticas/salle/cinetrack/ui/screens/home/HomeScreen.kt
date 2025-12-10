package nalgoticas.salle.cinetrack.ui.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nalgoticas.salle.cinetrack.models.Pelicula
import nalgoticas.salle.cinetrack.services.MovieService
import nalgoticas.salle.cinetrack.ui.screens.home.components.MovieGrid
import nalgoticas.salle.cinetrack.ui.theme.background
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



enum class MovieCategory(val label: String) {
    Trending("Trending"),
    Popular("Popular"),
    New("New")
}

@Composable
fun HomeScreen(
    onMovieClick: (Pelicula) -> Unit

) {

    val bg = background
    var movies by remember { mutableStateOf<List<Pelicula>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var search by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(MovieCategory.Trending) }

    LaunchedEffect(Unit) {
        isLoading = true
        error = null

        try {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api-app-peliculas.onrender.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(MovieService::class.java)

            val result = withContext(Dispatchers.IO) {
                service.getMovies()
            }
            movies = result
        } catch (e: Exception) {
            Log.e("HomeScreen", "Error: ${e.message}", e)
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
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFFFF4F6A))
                }
            }

            error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = error ?: "Error", color = Color.Red)
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = "CineTrack",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))

                    Spacer(Modifier.height(16.dp))

                    CategoryTabs(
                        selected = selectedCategory,
                        onSelectedChange = { selectedCategory = it }
                    )

                    Spacer(Modifier.height(16.dp))

                    val query = search.trim().lowercase()

                    val searched: List<Pelicula> = movies.filter {
                        it.title.lowercase().contains(query) ||
                                it.genre.lowercase().contains(query)
                    }

                    val moviesToShow = when (selectedCategory) {
                        MovieCategory.Trending ->
                            searched.sortedByDescending { it.rating }
                        MovieCategory.Popular ->
                            searched
                        MovieCategory.New ->
                            searched.sortedByDescending { it.year }
                    }

                    val filtered: List<Pelicula> = movies.filter { movie ->
                        val titleMatch = movie.title.lowercase().contains(query)
                        val genreMatch = movie.genre.any { g ->
                            g.lowercase().contains(query)
                        }
                        titleMatch || genreMatch
                    }
                    MovieGrid(
                        movies = moviesToShow,
                        modifier = Modifier.fillMaxSize(),
                        onMovieClick = onMovieClick
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryTabs(
    selected: MovieCategory,
    onSelectedChange: (MovieCategory) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(androidx.compose.foundation.shape.RoundedCornerShape(24.dp))
            .background(Color(0xCC151521))
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MovieCategory.values().forEach { category ->
                val isSelected = category == selected

                val brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFFF8A4D),
                        Color(0xFFFF4F6A)
                    )
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .clip(androidx.compose.foundation.shape.RoundedCornerShape(20.dp))
                        .background(
                            brush = if (isSelected) brush else Brush.linearGradient(
                                listOf(Color.Transparent, Color.Transparent)
                            ),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
                            alpha = 1f
                        )
                        .clickable { onSelectedChange(category) },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(
                            vertical = 6.dp,
                            horizontal = if (isSelected) 10.dp else 6.dp
                        )
                    ) {
                        if (category == MovieCategory.Trending) {
                            Icon(
                                imageVector = Icons.Filled.Whatshot,
                                contentDescription = null,
                                tint = if (isSelected) Color.White else Color(0xFF8A8A99),
                                modifier = Modifier
                                    .size(16.dp)
                                    .padding(end = 4.dp)
                            )
                        }

                        Text(
                            text = category.label,
                            color = if (isSelected) Color.White else Color(0xFF8A8A99),
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
