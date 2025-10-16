package rk.powermilk.clown.ui.camera

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import rk.powermilk.clown.util.Dimensions

@Composable
fun ClownNoseOverlay(modifier: Modifier) {
    Box(
        modifier = modifier
            .size(Dimensions.noseSize)
            .background(Color.Red, shape = CircleShape)
            .border(Dimensions.border, Color.Black, CircleShape)
    )
}
