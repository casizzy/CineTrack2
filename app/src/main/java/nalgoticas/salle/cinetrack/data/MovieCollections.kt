package nalgoticas.salle.cinetrack.data

import androidx.compose.runtime.mutableStateListOf

object MovieCollections {

    // ids de películas vistas
    val watchedIds = mutableStateListOf<Int>()

    // ids de películas favoritas
    val favoriteIds = mutableStateListOf<Int>()

    fun isWatched(id: Int): Boolean = watchedIds.contains(id)
    fun isFavorite(id: Int): Boolean = favoriteIds.contains(id)

    fun toggleWatched(id: Int) {
        if (watchedIds.contains(id)) {
            watchedIds.remove(id)
        } else {
            watchedIds.add(id)
        }
    }

    fun toggleFavorite(id: Int) {
        if (favoriteIds.contains(id)) {
            favoriteIds.remove(id)
        } else {
            favoriteIds.add(id)
        }
    }
}
