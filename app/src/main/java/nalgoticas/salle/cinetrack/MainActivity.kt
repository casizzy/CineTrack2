package nalgoticas.salle.cinetrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import nalgoticas.salle.cinetrack.ui.CineTrackApp
import nalgoticas.salle.cinetrack.ui.theme.CineTrackTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CineTrackTheme {
                CineTrackApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CineTrackPreview() {
    CineTrackTheme {
        CineTrackApp()
    }
}
