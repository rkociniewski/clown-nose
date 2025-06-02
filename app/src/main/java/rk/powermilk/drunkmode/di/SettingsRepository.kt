package rk.powermilk.drunkmode.di

import android.content.Context
import rk.powermilk.drunkmode.settings.Settings
import rk.powermilk.drunkmode.util.SettingsStore

class SettingsRepository(private val context: Context) {
    val settingsFlow = SettingsStore.read(context)
    suspend fun updateSettings(settings: Settings) = SettingsStore.write(context, settings)
}
