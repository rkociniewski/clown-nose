package rk.powermilk.clown.ui.helper

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import rk.powermilk.clown.enums.DisplayMode
import java.util.Locale

@Composable
fun rememberLocalizedContext(languageCode: String): Context {
    val baseContext = LocalContext.current
    val localConfiguration = LocalConfiguration.current
    return remember(languageCode) {
        val locale = Locale.Builder()
            .setLanguage(languageCode)
            .build()

        Locale.setDefault(locale)

        val config = Configuration(localConfiguration)
        config.setLocale(locale)

        baseContext.createConfigurationContext(config)
    }
}

@Composable
fun DisplayMode.isDarkTheme() = when (this) {
    DisplayMode.SYSTEM -> isSystemInDarkTheme()
    DisplayMode.DARK -> true
    DisplayMode.LIGHT -> false
}
