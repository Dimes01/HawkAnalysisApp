package hawk.analysis.app.nav

import kotlinx.serialization.Serializable

sealed interface Destination {

    @Serializable data object AuthGraph : Destination
    @Serializable data object LoginScreen : Destination
    @Serializable data object RegisterScreen : Destination

    @Serializable data object HomeGraph : Destination
    @Serializable data object HomeScreen : Destination
    @Serializable data object SettingsScreen : Destination
    @Serializable data object AccountScreen : Destination
}
