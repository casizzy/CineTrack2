package nalgoticas.salle.cinetrack.data

import androidx.compose.runtime.mutableStateMapOf

object MovieCollections {


    private val watched = mutableStateMapOf<Int, Boolean>()
    private val favorites = mutableStateMapOf<Int, Boolean>()

    fun isWatched(id: Int): Boolean = watched[id] == true

    fun toggleWatched(id: Int) {
        val current = watched[id] ?: false
        watched[id] = !current
    }

    fun isFavorite(id: Int): Boolean = favorites[id] == true

    fun toggleFavorite(id: Int) {
        val current = favorites[id] ?: false
        favorites[id] = !current
    }
}
