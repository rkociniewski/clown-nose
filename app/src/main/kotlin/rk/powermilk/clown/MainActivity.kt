package rk.powermilk.clown

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import rk.powermilk.clown.ui.clownNose.ClownNoseScreen
import rk.powermilk.clown.ui.helper.isDarkTheme
import rk.powermilk.clown.ui.helper.rememberLocalizedContext
import rk.powermilk.clown.ui.theme.ClownNoseTheme
import rk.powermilk.clown.viewmodel.ClownNoseViewModel

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
            val viewModel: ClownNoseViewModel = hiltViewModel()
            val settings by viewModel.settings.collectAsState()

            val darkTheme = settings.displayMode.isDarkTheme()

            val localizedContext = rememberLocalizedContext(settings.language.name.lowercase())
            CompositionLocalProvider(LocalContext provides localizedContext) {
                ClownNoseTheme(darkTheme) {
                    ClownNoseScreen(
                        viewModel = viewModel,
                        localizedContext = localizedContext
                    )
                }
            }
        }
    }
}
