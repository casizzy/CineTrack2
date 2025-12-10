package nalgoticas.salle.cinetrack.ui.screens.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RatingStars(rating: Float, modifier: Modifier = Modifier) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(5) { index ->
            val filled = index < rating.toInt()
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = if (filled) Color(0xFFFFC045) else {
                    Color(0xFF3A3A4A)
                },
                modifier = Modifier.size(14.dp)
            )
        }
    }
}