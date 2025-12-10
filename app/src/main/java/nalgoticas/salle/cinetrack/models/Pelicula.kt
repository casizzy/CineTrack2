package nalgoticas.salle.cinetrack.models

import java.io.Serializable

data class Pelicula(
    val id: Int,
    val title: String,
    val year: Int,
    val duration_minutes: Int,
    val genre: String,
    val rating: Float,
    val image_url: String,
    val synopsis: String,
    val director: String,
    val cast: String
) :Serializable
