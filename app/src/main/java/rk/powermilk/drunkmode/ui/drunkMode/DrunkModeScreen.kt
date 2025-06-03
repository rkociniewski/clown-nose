package rk.powermilk.drunkmode.ui.drunkMode

import android.app.Activity
import android.content.Context
import android.view.WindowManager
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.hilt.navigation.compose.hiltViewModel
import rk.powermilk.drunkmode.ui.settings.SettingsDialog
import rk.powermilk.drunkmode.viewmodel.DrunkModeViewModel

@Composable
fun DrunkModeScreen(
    onConfirmedDrunk: () -> Unit,
    onAskChallenge: () -> Unit,
    viewModel: DrunkModeViewModel = hiltViewModel(),
    localizedContext: Context,
) {
    val settings by viewModel.settings.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    var showSettingsDialog by remember { mutableStateOf(false) }
    val view = LocalView.current

    DisposableEffect(Unit) {
        val window = (view.context as? Activity)?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        onDispose {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            DrunkModeTopAppBar(
                settings = settings,
                onSettingsClick = { showSettingsDialog = true }
            )
        }
    ) {
        QuestionContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            onConfirmedDrunk = {
                viewModel.confirmDrunk()
                onConfirmedDrunk()
            },
            onAskChallenge = {
                viewModel.askForChallenge()
                onAskChallenge()
            },
        )

        if (showSettingsDialog) {
            SettingsDialog(
                settings = settings,
                onSettingsUpdate = { viewModel.updateSettings(it) },
                onDismiss = {
                    showSettingsDialog = false
                    viewModel.updateSettings(settings)
                },
                localizedContext = localizedContext,
            )
        }
    }
}
