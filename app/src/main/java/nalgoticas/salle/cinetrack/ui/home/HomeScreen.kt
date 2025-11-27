package nalgoticas.salle.cinetrack.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Whatshot
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nalgoticas.salle.cinetrack.R
import coil.compose.AsyncImage



data class Movie(
    val id: Int,
    val title: String,
    val year: Int,
    val genre: String,
    val rating: Float,
    val imageUrl: String
)

private val trendingMovies = listOf(
    Movie(
        id = 1,
        title = "Parasite",
        year = 2019,
        genre = "Drama",
        rating = 4.7f,
        imageUrl = "https://image.tmdb.org/t/p/w500/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg"
    ),
    Movie(
        id = 2,
        title = "Interstellar",
        year = 2014,
        genre = "Adventure",
        rating = 4.5f,
        imageUrl = "https://image.tmdb.org/t/p/w500/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg"
    ),
    Movie(
        id = 3,
        title = "Inception",
        year = 2010,
        genre = "Action",
        rating = 4.6f,
        imageUrl = "https://image.tmdb.org/t/p/w500/edv5CZvWj09upOsy2Y6IwDhK8bt.jpg"
    ),
    Movie(
        id = 4,
        title = "The Dark Knight",
        year = 2008,
        genre = "Action",
        rating = 4.7f,
        imageUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg"
    )
)



enum class MovieCategory(val label: String) {
    Trending("Trending"),
    Popular("Popular"),
    New("New")
}

@Composable
fun HomeScreen() {
    val bg = Color(0xFF050510)
    var selectedCategory by remember { mutableStateOf(MovieCategory.Trending) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        CineTrackTopBar()
        Spacer(Modifier.height(8.dp))
        SearchField()
        Spacer(Modifier.height(16.dp))
        CategoryTabs(
            selected = selectedCategory,
            onSelectedChange = { selectedCategory = it }
        )
        Spacer(Modifier.height(16.dp))
        MovieGrid(trendingMovies)
    }
}


@Composable
private fun CineTrackTopBar() {
    Text(
        text = "CineTrack",
        color = Color.White,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun SearchField() {
    OutlinedTextField(
        value = "",
        onValueChange = { },
        placeholder = {
            Text(
                text = "Search films...",
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
        shape = RoundedCornerShape(24.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color(0xFFFF6B3D),
            unfocusedContainerColor = Color(0xFF12121E),
            focusedContainerColor = Color(0xFF12121E),
            cursorColor = Color.White
        )
    )
}


@Composable
private fun CategoryTabs(
    selected: MovieCategory,
    onSelectedChange: (MovieCategory) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Color(0xCC151521)
            )
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
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            brush = if (isSelected) brush else Brush.linearGradient(
                                listOf(Color.Transparent, Color.Transparent)
                            ),
                            shape = RoundedCornerShape(20.dp),
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
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MovieGrid(movies: List<Movie>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(movies, key = { it.id }) { movie ->
            MovieCard(movie)
        }
    }
}

@Composable
private fun MovieCard(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF262636))
        ) {
            AsyncImage(
                model = movie.imageUrl,
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
                    .background(Color(0xFF1AC98A))
            ) {
                Icon(
                    imageVector = Icons.Filled.Visibility,
                    contentDescription = "Seen",
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
                    .background(Color(0xFFFFC045))
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
