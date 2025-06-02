package rk.powermilk.drunkmode.ui.main

import android.app.Activity
import android.view.WindowManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import rk.powermilk.drunkmode.R
import rk.powermilk.drunkmode.util.Dimensions
import rk.powermilk.drunkmode.viewmodel.DrunkModeViewModel

@Composable
fun MainScreen(
    viewModel: DrunkModeViewModel = hiltViewModel(),
    onConfirmedDrunk: () -> Unit,
    onAskChallenge: () -> Unit,
) {
    val view = LocalView.current

    DisposableEffect(Unit) {
        val window = (view.context as? Activity)?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        onDispose {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.main_screen_question),
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(Dimensions.spacerHeight))
                Row(horizontalArrangement = Arrangement.spacedBy(Dimensions.dialogPadding)) {
                    Button(onClick = {
                        viewModel.confirmDrunk()
                        onConfirmedDrunk()
                    }) {
                        Text(stringResource(R.string.yes))
                    }
                    Button(onClick = {
                        viewModel.askForChallenge()
                        onAskChallenge()
                    }) {
                        Text(stringResource(R.string.no))
                    }
                }
            }
        }
    }
}
