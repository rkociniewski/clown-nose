package rk.powermilk.drunkmode

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import rk.powermilk.drunkmode.ui.helper.isDarkTheme
import rk.powermilk.drunkmode.ui.main.MainScreen
import rk.powermilk.drunkmode.ui.theme.DrunkModeTheme
import rk.powermilk.drunkmode.viewmodel.DrunkModeViewModel

/**
 * MainActivity.kt
 *
 * The application's main entry point activity.
 * Sets up the UI theme based on configuration and initializes the main pericope screen.
 */

/**
 * Main activity that initializes the application UI.
 * Sets up the theme based on user preferences and displays the pericope screen.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    /**
     * Sets up the activity with the appropriate theme and content.
     *
     * @param savedInstanceState If the activity is being re-initialized, this Bundle
     * contains the data it most recently supplied in onSaveInstanceState.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        enableEdgeToEdge()

        setContent {
            val viewModel: DrunkModeViewModel = hiltViewModel()
            val settings by viewModel.settings.collectAsState()

            val darkTheme = settings.displayMode.isDarkTheme()

            DrunkModeTheme(darkTheme) {
                var screen by remember { mutableStateOf("question") }

                when (screen) {
                    "question" -> MainScreen(
                        onConfirmedDrunk = { screen = "blocked" },
                        onAskChallenge = { screen = "challenge" }
                    )

                    "blocked" -> Text("Apps are blocked now.") // To podmienisz na właściwy ekran
                    "challenge" -> Text("Solve a math challenge...") // Tu pojawi się kolejne UI
                }
            }
        }
    }
}
