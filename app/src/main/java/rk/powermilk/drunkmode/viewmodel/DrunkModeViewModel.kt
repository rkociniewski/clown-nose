package rk.powermilk.drunkmode.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import rk.powermilk.drunkmode.di.SettingsRepository
import rk.powermilk.drunkmode.settings.Settings
import rk.powermilk.drunkmode.util.AppLogger
import rk.powermilk.drunkmode.util.LogTags

sealed class DrunkModeState {
    data object Idle : DrunkModeState()
    data object DrunkConfirmed : DrunkModeState()
    data object AskChallenge : DrunkModeState()
}

@HiltViewModel
class DrunkModeViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val logger = AppLogger(this::class.simpleName ?: LogTags.DRUNK_MODE_VIEW_MODEL)

    private val _settings = MutableStateFlow(Settings())
    val settings: StateFlow<Settings> = _settings

    private val _uiState = MutableStateFlow<DrunkModeState>(DrunkModeState.Idle)
    val uiState: StateFlow<DrunkModeState> = _uiState

    init {
        viewModelScope.launch {
            settingsRepository.settingsFlow.collect {
                _settings.value = it
            }
        }
    }

    fun confirmDrunk() {
        _uiState.value = DrunkModeState.DrunkConfirmed
    }

    fun askForChallenge() {
        _uiState.value = DrunkModeState.AskChallenge
    }
}
