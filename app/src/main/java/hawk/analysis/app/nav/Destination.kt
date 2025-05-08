package hawk.analysis.app.nav

import kotlinx.serialization.Serializable

sealed interface Destination {

    @Serializable data object AuthGraph : Destination
    @Serializable data object LoginScreen : Destination
    @Serializable data object RegisterScreen : Destination

    @Serializable data object HomeGraph : Destination
    @Serializable data object HomeScreen : Destination
    @Serializable data class AccountScreen(val accountId: String) : Destination
    @Serializable data class AssetScreen(val ticker: String) : Destination

    @Serializable data object SettingsGraph : Destination
    @Serializable data object SettingsScreen : Destination
    @Serializable data object ChangeUserSettings : Destination
    @Serializable data class ChangeAccountScreen(val accountId: String) : Destination
    @Serializable data object AddAuthTokenScreen : Destination
    @Serializable data object EditAuthTokenScreen : Destination
}
