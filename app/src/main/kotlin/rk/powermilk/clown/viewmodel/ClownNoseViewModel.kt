package rk.powermilk.clown.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import rk.powermilk.clown.di.SettingsRepository
import rk.powermilk.clown.settings.Settings
import rk.powermilk.clown.util.AppLogger
import rk.powermilk.clown.util.LogTags

@HiltViewModel
class ClownNoseViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val logger = AppLogger(this::class.simpleName ?: LogTags.CLOWN_NOSE_VIEW_MODEL)

    private val _settings = MutableStateFlow(Settings())
    val settings: StateFlow<Settings> = _settings

    init {
        viewModelScope.launch {
            settingsRepository.settingsFlow.collect {
                _settings.value = it
            }
        }
    }

    fun updateSettings(settings: Settings) {
        viewModelScope.launch {
            val writeSuccess = settingsRepository.updateSettings(settings)

            if (writeSuccess) {
                _settings.value = settings

                logger.debug("Settings updated: $settings")
            } else {
                logger.error("Failed to persist settings")
            }
        }
    }
}
