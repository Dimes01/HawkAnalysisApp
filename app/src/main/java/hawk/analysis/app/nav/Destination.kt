package hawk.analysis.app.nav

import kotlinx.serialization.Serializable

sealed interface Destination {

    @Serializable data object AuthGraph : Destination
    @Serializable data object LoginScreen : Destination
    @Serializable data object RegisterScreen : Destination

    @Serializable data object HomeGraph : Destination
    @Serializable data object HomeScreen : Destination
    @Serializable data class AccountScreen(val accountId: String, val authToken: String) : Destination
    @Serializable data class AssetScreen(val accountId: String, val uid: String, val authToken: String) : Destination

    @Serializable data object SettingsGraph : Destination
    @Serializable data object SettingsScreen : Destination
    @Serializable data object EditEmailScreen : Destination
    @Serializable data object EditPasswordScreen : Destination
    @Serializable data class EditAccountScreen(val authToken: String, val accountId: String) : Destination
    @Serializable data object AddAuthTokenScreen : Destination
    @Serializable data class EditAuthTokenScreen(val tokenId: Int) : Destination
    @Serializable data class FindInstrumentScreen(val authToken: String) : Destination

    @Serializable data object InformationGraph : Destination
    @Serializable data object InformationScreen : Destination
}
