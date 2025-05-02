package hawk.analysis.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavGraph.Companion.findStartDestination
import hawk.analysis.app.nav.Destination
import hawk.analysis.app.nav.Navigator
import kotlinx.coroutines.launch

class HomeViewModel(
    private val navigator: Navigator,
) : ViewModel() {
    companion object {
        val NAVIGATOR_KEY = object : CreationExtras.Key<Navigator> {}
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val navigator = this[NAVIGATOR_KEY] as Navigator
                HomeViewModel(navigator)
            }
        }
    }

    fun previousAccount() {

    }

    fun nextAccount() {

    }

    fun toAnalyseAccount() {
        viewModelScope.launch {
            navigator.navigate(Destination.LoginScreen) {
                popUpTo(navigator.startDestination) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    fun toSettings() {
        viewModelScope.launch {
            navigator.navigate(Destination.SettingsScreen) {
                popUpTo(navigator.startDestination) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }
    }
}
