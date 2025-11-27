package nalgoticas.salle.cinetrack.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import nalgoticas.salle.cinetrack.data.MovieData

/**
 * Llamas a este composable desde el NavHost:
 *
 * composable("details/{movieId}", ...) { backStackEntry ->
 *     val id = backStackEntry.arguments?.getInt("movieId") ?: return@composable
 *     MovieDetailScreen(
 *         movieId = id,
 *         onBack = { navController.popBackStack() }
 *     )
 * }
 */
@Composable
fun MovieDetailScreen(
    movieId: Int,
    onBack: () -> Unit
) {
    val movie = MovieData.getMovie(movieId) ?: return

    val bg = Color(0xFF050510)
    var yourRating by remember { mutableStateOf(movie.rating.toInt()) }
    var review by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
            ) {
                AsyncImage(
                    model = movie.imageUrl,
                    contentDescription = movie.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0xAA000000),
                                    Color.Transparent,
                                    Color(0xFF050510)
                                )
                            )
                        )
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color(0x88000000))
                        .clickable { onBack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = movie.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "${movie.year}  •  ${movie.durationMinutes} min",
                    fontSize = 13.sp,
                    color = Color(0xFFB0B0C0)
                )

                Spacer(Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(Color(0xFF3A260F))
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFC045),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = "${movie.rating}",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = " / 5.0",
                                color = Color(0xFFB0B0C0),
                                fontSize = 13.sp
                            )
                        }
                    }

                    Spacer(Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        Color(0xFF15D37B),
                                        Color(0xFF0CAF62)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = "Watched",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(Color(0xFF0D0D16)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = null,
                            tint = Color(0xFFFF4F6A)
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))
                Text(
                    text = "Your Rating",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
                Spacer(Modifier.height(8.dp))

                Row {
                    repeat(5) { index ->
                        val filled = index < yourRating
                        Box(
                            modifier = Modifier
                                .padding(end = 6.dp)
                                .size(38.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF1E1306))
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFFFC045),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable {
                                    yourRating = index + 1
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                tint = if (filled) Color(0xFFFFC045) else Color(0xFF5A4A26)
                            )
                        }
                    }
                }
                Spacer(Modifier.height(18.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    movie.genres.forEachIndexed { index, genre ->
                        GenreChip(
                            text = genre,
                            isPrimary = index == 0
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))
                SectionTitle("Synopsis")
                Spacer(Modifier.height(6.dp))
                SectionBody(movie.synopsis)

                Spacer(Modifier.height(18.dp))
                SectionTitle("Director")
                Spacer(Modifier.height(4.dp))
                SectionBody(movie.director)

                Spacer(Modifier.height(18.dp))
                SectionTitle("Cast")
                Spacer(Modifier.height(4.dp))
                SectionBody(movie.cast)

                Spacer(Modifier.height(22.dp))
                SectionTitle("Your Review")
                Spacer(Modifier.height(10.dp))

                TextField(
                    value = review,
                    onValueChange = { review = it },
                    placeholder = {
                        Text(
                            "Write your review...",
                            color = Color(0xFF8A8A99)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(18.dp)),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFF10101A),
                        focusedContainerColor = Color(0xFF10101A),
                        disabledContainerColor = Color(0xFF10101A),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.White
                    )
                )

                Spacer(Modifier.height(18.dp))

                Box(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    Color(0xFFFF8A3C),
                                    Color(0xFFFF4F6A)
                                )
                            )
                        )
                        .padding(horizontal = 24.dp, vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Submit",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun GenreChip(text: String, isPrimary: Boolean) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (isPrimary) Color(0xFF162641) else Color(0xFF11111C)
            )
            .border(
                width = 1.dp,
                color = if (isPrimary) Color(0xFF4FC3F7) else Color(0xFF303040),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 14.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
private fun SectionBody(text: String) {
    Text(
        text = text,
        color = Color(0xFFDFDFE6),
        fontSize = 13.sp,
        lineHeight = 18.sp
    )
}
