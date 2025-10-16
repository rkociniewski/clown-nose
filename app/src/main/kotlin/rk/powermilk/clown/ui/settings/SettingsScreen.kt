package rk.powermilk.clown.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import rk.powermilk.clown.R
import rk.powermilk.clown.enums.DisplayMode
import rk.powermilk.clown.enums.Language
import rk.powermilk.clown.settings.Settings
import rk.powermilk.clown.ui.helper.BooleanSelector
import rk.powermilk.clown.ui.helper.EnumSelector
import rk.powermilk.clown.ui.helper.HelpLabel
import rk.powermilk.clown.ui.helper.LanguageSelector
import rk.powermilk.clown.ui.helper.SettingsSlider
import rk.powermilk.clown.util.Dimensions
import rk.powermilk.clown.util.Numbers

/**
 * A composable function that displays the configuration section in a modal dialog.
 *
 * This component provides UI controls for all application settings including additional mode options,
 * display mode preferences, and draw mode settings.
 *
 * @param settings The current configuration being edited
 * @param updateSettings Callback to update the configuration when changes are made
 * @param onClose Callback to close the configuration dialog
 */
@Composable
fun SettingsScreen(
    settings: Settings,
    updateSettings: (Settings) -> Unit,
    onClose: () -> Unit,
) {
    Column(
        Modifier.padding(Dimensions.dialogPadding),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        HelpLabel(
            stringResource(R.string.settings_label_language),
            stringResource(R.string.tooltip_language)

        )
        LanguageSelector(Language.entries, settings.language) {
            updateSettings(settings.copy(language = it))
        }

        HorizontalDivider()

        HelpLabel(
            stringResource(R.string.settings_label_display_mode),
            stringResource(R.string.tooltip_display_mode)
        )
        EnumSelector(DisplayMode.entries, settings.displayMode) {
            updateSettings(settings.copy(displayMode = it))
        }

        HorizontalDivider()

        BooleanSelector(settings.timedChallenge, stringResource(R.string.settings_timed_challenged)) {
            updateSettings(settings.copy(timedChallenge = it))
        }

        HorizontalDivider()

        HelpLabel(
            stringResource(R.string.settings_label_blocked_time),
            stringResource(R.string.tooltip_blocked_time)
        )
        SettingsSlider(settings.blockedTime, (Numbers.TWENTY..Numbers.SIXTY step Numbers.FIVE)) {
            updateSettings(settings.copy(blockedTime = it))
        }

        HorizontalDivider()

        HelpLabel(
            stringResource(R.string.settings_label_next_challenge_time),
            stringResource(R.string.tooltip_next_challenge_time)
        )
        SettingsSlider(settings.nextChallengeTime, (Numbers.FIVE..Numbers.TWENTY step Numbers.FIVE)) {
            updateSettings(settings.copy(nextChallengeTime = it))
        }

        HorizontalDivider()

        if (settings.timedChallenge) {
            HelpLabel(
                stringResource(R.string.settings_label_challenge_time),
                stringResource(R.string.tooltip_challenge_time)
            )
            SettingsSlider(settings.challengeTime, (Numbers.THIRTY..Numbers.SIXTY step Numbers.FIVE)) {
                updateSettings(settings.copy(challengeTime = it))
            }
        }

        Spacer(Modifier.height(Dimensions.height))

        Button(
            onClose,
            Modifier
                .fillMaxWidth()
                .padding(top = Dimensions.height)
        ) {
            Text(stringResource(R.string.close))
        }
    }
}
