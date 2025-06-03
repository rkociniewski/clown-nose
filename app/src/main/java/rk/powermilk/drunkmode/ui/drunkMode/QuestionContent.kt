package rk.powermilk.drunkmode.ui.drunkMode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import rk.powermilk.drunkmode.R
import rk.powermilk.drunkmode.util.Dimensions


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
    onConfirmedDrunk: () -> Unit,
    onAskChallenge: () -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.main_screen_question),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(Dimensions.spacerHeight))
            Row(horizontalArrangement = Arrangement.spacedBy(Dimensions.dialogPadding)) {
                Button(
                    onClick = {
                        onConfirmedDrunk()
                    }
                ) {
                    Text(stringResource(R.string.yes))
                }
                Button(
                    onClick = {
                        onAskChallenge()
                    }
                ) {
                    Text(stringResource(R.string.no))
                }
            }
        }
    }
}
