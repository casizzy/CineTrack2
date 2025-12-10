package nalgoticas.salle.cinetrack.ui.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

@Composable
fun DiscoverScreen(
    onMovieClick: (Pelicula) -> Unit
) {
    val bg = background
    var movies by remember { mutableStateOf<List<Pelicula>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var search by remember { mutableStateOf("") }

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
            Log.e("DiscoverScreen", "Error: ${e.message}", e)
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = "Discover",
                        color = Color.White,
                        fontSize = 22.sp
                    )

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = search,
                        onValueChange = { search = it },
                        placeholder = {
                            Text(
                                text = "Search movies or genres...",
                                color = Color(0xFF8A8A99)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search",
                                tint = Color(0xFF8A8A99)
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color(0xFFFF6B3D),
                            unfocusedContainerColor = Color(0xFF12121E),
                            focusedContainerColor = Color(0xFF12121E),
                            cursorColor = Color.White
                        )
                    )

                    Spacer(Modifier.height(16.dp))

                    val query = search.trim().lowercase()

                    val searched: List<Pelicula> = movies.filter {
                        it.title.lowercase().contains(query) ||
                                it.genre.lowercase().contains(query)
                    }

                    val filtered: List<Pelicula> = movies.filter { movie ->
                        val titleMatch = movie.title.lowercase().contains(query)
                        val genreMatch = movie.genre.any { g ->
                            g.lowercase().contains(query)
                        }
                        titleMatch || genreMatch
                    }


                    MovieGrid(
                        movies = filtered,
                        modifier = Modifier.fillMaxSize(),
                        onMovieClick = onMovieClick
                    )
                }
            }
        }
    }
}
