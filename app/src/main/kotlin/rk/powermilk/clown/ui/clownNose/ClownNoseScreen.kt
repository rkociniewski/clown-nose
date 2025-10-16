@file:Suppress("UnusedParameter")

package rk.powermilk.clown.ui.clownNose

import android.app.Activity
import android.content.Context
import android.view.WindowManager
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import rk.powermilk.clown.ui.camera.QuestionContent
import rk.powermilk.clown.viewmodel.ClownNoseViewModel

@Composable
fun ClownNoseScreen(
    viewModel: ClownNoseViewModel = hiltViewModel(),
    localizedContext: Context,
) {
    val view = LocalView.current

    DisposableEffect(Unit) {
        val window = (view.context as? Activity)?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        onDispose {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    Scaffold() {
        QuestionContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            onCapturePhoto = { },
            onStartRecording = { },
            onStopRecording = { },
            isRecording = false,
        )
    }
}
