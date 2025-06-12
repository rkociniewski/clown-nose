package rk.powermilk.clown.ui.camera

import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import rk.powermilk.clown.R
import rk.powermilk.clown.util.Dimensions

/**
 * Main content area displaying the list of pericopes.
 *
 * This composable creates a scrollable column of pericope items, with the selected
 * pericope visually distinguished from others.
 *
 * @param modifier Modifier to be applied to the content container
 */
@Composable
fun QuestionContent(
    modifier: Modifier = Modifier,
    onCapturePhoto: () -> Unit,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    isRecording: Boolean,
) {
    Box(modifier = modifier) {
        AndroidView(
            factory = { context -> PreviewView(context) },
            modifier = modifier
        )

        ClownNoseOverlay(
            Modifier
                .align(Alignment.Center)
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(Dimensions.spacerHeight),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = onCapturePhoto) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = stringResource(R.string.take_photo),
                    modifier = Modifier.size(Dimensions.minHeightIn)
                )
            }
            Spacer(modifier = Modifier.height(Dimensions.dialogPadding))
            IconButton(onClick = {
                if (isRecording) onStopRecording() else onStartRecording()
            }) {
                Icon(
                    imageVector = if (isRecording) Icons.Default.Stop else Icons.Default.Videocam,
                    contentDescription = stringResource(R.string.record_video),
                    modifier = Modifier.size(Dimensions.minHeightIn)
                )
            }
        }
    }
}
