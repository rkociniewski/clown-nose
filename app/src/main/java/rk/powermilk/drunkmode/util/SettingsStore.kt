package rk.powermilk.drunkmode.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import rk.powermilk.drunkmode.enums.DisplayMode
import rk.powermilk.drunkmode.settings.Settings
import rk.powermilk.drunkmode.settings.resolveDefaultLanguage

/**
 * Manages configuration data persistence using Jetpack DataStore preferences.
 * Provides functionality to read and write application configuration settings.
 */
private val Context.dataStore by preferencesDataStore("settings")

/**
 * Object responsible for managing application configuration data.
 * Uses Android's DataStore preferences for persisting configuration values.
 */
object SettingsStore {
    private val logger = AppLogger(this::class.simpleName ?: LogTags.SETTINGS_STORE)

    private val LANGUAGE = stringPreferencesKey("language")
    private val DISPLAY_MODE = stringPreferencesKey("display_mode")
    private val TIMED_CHALLENGE = booleanPreferencesKey("timed_challenge")
    private val BLOCKED_TIME = intPreferencesKey("blocked_time")
    private val NEXT_CHALLENGE_TIME = intPreferencesKey("next_challenge_time")
    private val CHALLENGE_TIME = intPreferencesKey("challenge_time")

    fun read(context: Context): Flow<Settings> = context.dataStore.data.map {
        val settings = Settings(
            safeEnumValueOf(it[LANGUAGE], resolveDefaultLanguage()),
            safeEnumValueOf(it[DISPLAY_MODE], DisplayMode.SYSTEM),
            it[TIMED_CHALLENGE] == false,
            it[BLOCKED_TIME] ?: Numbers.FIFTEEN,
            it[NEXT_CHALLENGE_TIME] ?: Numbers.FIFTEEN,
            it[CHALLENGE_TIME] ?: Numbers.FIFTEEN,
        )
        logger.debug("Loaded settings: $settings")
        settings
    }.catch {
        logger.error("Error reading settings", it)
        emit(Settings())
    }

    @Suppress("TooGenericExceptionCaught")
    suspend fun write(context: Context, settings: Settings) = try {
        context.dataStore.edit {
            it[LANGUAGE] = settings.language.name
            it[DISPLAY_MODE] = settings.displayMode.name
            it[TIMED_CHALLENGE] = settings.timedChallenge
            it[BLOCKED_TIME] = settings.blockedTime
            it[NEXT_CHALLENGE_TIME] = settings.nextChallengeTime
            it[CHALLENGE_TIME] = settings.challengeTime
            context.getSharedPreferences("settings", Context.MODE_PRIVATE).edit()
                .putString("language", settings.language.name.lowercase())
                .apply()
        }
        true
    } catch (e: Exception) {
        logger.error("Error writing settings", e)
        false
    }
}

// Safe fallback for enums
private inline fun <reified T : Enum<T>> safeEnumValueOf(name: String?, default: T): T {
    return enumValues<T>().firstOrNull { it.name == name } ?: default
}
