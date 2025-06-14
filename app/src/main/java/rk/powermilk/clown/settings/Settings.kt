package rk.powermilk.clown.settings

import rk.powermilk.clown.enums.DisplayMode
import rk.powermilk.clown.enums.Language
import java.util.Locale

data class Settings(
    val language: Language = resolveDefaultLanguage(),
    val displayMode: DisplayMode = DisplayMode.SYSTEM,
    val timedChallenge: Boolean = false,
    val blockedTime: Int = 30,
    val nextChallengeTime: Int = 15,
    val challengeTime: Int = 15,
)

fun resolveDefaultLanguage(): Language {
    val systemLangCode = Locale.getDefault().language.uppercase()
    return Language.entries.firstOrNull { it.name == systemLangCode } ?: Language.EN
}
