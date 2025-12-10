package nalgoticas.salle.cinetrack.services

import nalgoticas.salle.cinetrack.models.Pelicula
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET("movies")
    suspend fun getMovies(): List<Pelicula>

    @GET("movies/{id}")
    suspend fun getMovieDetail(@Path("id") id: String): Pelicula
}
