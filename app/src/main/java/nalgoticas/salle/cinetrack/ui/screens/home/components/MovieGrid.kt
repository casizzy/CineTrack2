package nalgoticas.salle.cinetrack.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nalgoticas.salle.cinetrack.data.MovieCollections
import nalgoticas.salle.cinetrack.models.Pelicula

@Composable
fun MovieGrid(
    movies: List<Pelicula>,
    modifier: Modifier = Modifier,
    enableActions: Boolean = false,
    onMovieClick: (Pelicula) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 80.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            count = movies.size,
            key = { movies[it].id }
        ) { index ->
            val movie = movies[index]
            MovieCard(
                movie = movie,
                watched = MovieCollections.isWatched(movie.id),
                favorite = MovieCollections.isFavorite(movie.id),
                onClick = { onMovieClick(movie) }
            )
        }
    }
}