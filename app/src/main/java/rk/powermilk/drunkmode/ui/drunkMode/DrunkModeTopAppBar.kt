package rk.powermilk.drunkmode.ui.drunkMode

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import rk.powermilk.drunkmode.R
import rk.powermilk.drunkmode.settings.Settings

/**
 * Top app bar for the pericope screen.
 *
 * This composable creates the application's top bar, which includes the app title and
 * action buttons for drawing new pericopes and accessing configuration options.
 *
 * @param settings The current application configuration
 * @param onSettingsClick Callback invoked when the configuration button is clicked
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrunkModeTopAppBar(
    settings: Settings,
    onSettingsClick: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = { Text(stringResource(R.string.app_name)) },
        actions = {
            IconButton(onClick = onSettingsClick) {
                Icon(
                    Icons.Outlined.Settings,
                    contentDescription = stringResource(R.string.settings),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    )
}
