package rk.powermilk.clown.ui.helper

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults.rememberPlainTooltipPositionProvider
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import rk.powermilk.clown.R
import rk.powermilk.clown.enums.DisplayText
import rk.powermilk.clown.enums.Language
import rk.powermilk.clown.util.Dimensions
import kotlin.enums.EnumEntries

/**
 * A composable function that creates a row of selectable filter chips for enum options.
 *
 * This generic component creates a horizontal row of filter chips based on the provided enum options.
 * It highlights the currently selected option and invokes the onSelect callback when a different option is chosen.
 *
 * @param T The enum type that implements DisplayText interface
 * @param options The enum entries to display as options
 * @param selected The currently selected enum value
 * @param onSelect Callback invoked when a different option is selected
 */
@Composable
fun <T> EnumSelector(
    options: EnumEntries<T>,
    selected: T,
    onSelect: (T) -> Unit
) where T : Enum<T>, T : DisplayText =
    Row(horizontalArrangement = Arrangement.spacedBy(Dimensions.height)) {
        options.forEach {
            FilterChip(
                selected == it,
                { onSelect(it) },
                { Text(stringResource(it.label)) },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    labelColor = MaterialTheme.colorScheme.onSurface,
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    }

/**
 * A composable function that displays a label with an information tooltip.
 *
 * This component pairs a text label with an information icon. When the icon is clicked,
 * it displays a tooltip with additional helpful information.
 *
 * @param label The primary text to display as the label
 * @param tooltip The text to display in the tooltip when the info icon is clicked
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpLabel(label: String, tooltip: String) {
    val tooltipState = rememberTooltipState(isPersistent = true)
    val scope = rememberCoroutineScope()

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(label, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.width(Dimensions.minHeight))
        TooltipBox(
            rememberPlainTooltipPositionProvider(), {
                PlainTooltip { Text(tooltip) }
            }, tooltipState
        ) {
            Icon(
                Icons.Outlined.Info, stringResource(R.string.tooltip_icon_description),
                Modifier
                    .padding(Dimensions.minHeight)
                    .clickable {
                        scope.launch {
                            if (!tooltipState.isVisible) {
                                tooltipState.show()
                            } else {
                                tooltipState.dismiss()
                            }
                        }
                    },
                MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun BooleanSelector(
    boolean: Boolean,
    label: String,
    onCheckedChange: ((Boolean) -> Unit)?
) = Row(verticalAlignment = Alignment.CenterVertically) {
    Checkbox(boolean, onCheckedChange)
    Spacer(Modifier.width(Dimensions.height))
    Text(label)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelector(
    options: EnumEntries<Language>,
    selected: Language,
    onSelect: (Language) -> Unit
) {
    LazyVerticalGrid(
        GridCells.Adaptive(minSize = Dimensions.adaptiveMinSize), Modifier
            .fillMaxWidth()
            .heightIn(max = Dimensions.maxHeightIn),
        verticalArrangement = Arrangement.spacedBy(Dimensions.height),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.height)
    ) {
        items(options.size) {
            val opt = options[it]

            FilterChip(
                selected == opt, { onSelect(opt) }, {
                    TooltipBox(
                        rememberPlainTooltipPositionProvider(),
                        { Text(stringResource(opt.label)) },
                        rememberTooltipState()
                    ) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = Dimensions.minHeight),
                            Alignment.Center
                        ) {
                            Icon(
                                painterResource(opt.flagIcon),
                                stringResource(opt.label),
                                Modifier.size(Dimensions.size),
                                Color.Unspecified
                            )
                        }
                    }
                }, colors = FilterChipDefaults.filterChipColors(
                    MaterialTheme.colorScheme.surfaceVariant,
                    MaterialTheme.colorScheme.onSurface,
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                border = null,
                shape = RoundedCornerShape(Dimensions.dialogPadding)
            )
        }
    }
}

/**
 * A composable function that displays a slider with a text value indicator.
 *
 * This component provides a slider control for numerical values with a text display
 * of the current value centered beneath the slider.
 *
 * @param value The current integer value of the slider
 * @param range The range of possible values for the slider with a specified step
 * @param onValueChange Callback invoked when the slider value changes
 */
@Composable
fun SettingsSlider(value: Int, range: IntProgression = 0..2 step 1, onValueChange: (Int) -> Unit) {
    Column {
        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = range.first.toFloat()..range.last.toFloat(),
            steps = ((range.last - range.first) / range.step) - 1
        )
        Text("$value", modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}
