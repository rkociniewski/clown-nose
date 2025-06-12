package rk.powermilk.clown.di

import android.content.Context
import rk.powermilk.clown.settings.Settings
import rk.powermilk.clown.util.SettingsStore

class SettingsRepository(private val context: Context) {
    val settingsFlow = SettingsStore.read(context)
    suspend fun updateSettings(settings: Settings) = SettingsStore.write(context, settings)
}
