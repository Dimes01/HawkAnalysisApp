package hawk.analysis.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hawk.analysis.app.nav.Destination
import hawk.analysis.app.nav.Navigator
import hawk.analysis.app.screens.SettingsScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val navigator: Navigator,

) : ViewModel()  {
    private val _state = MutableStateFlow(SettingsScreenState())
    val state: StateFlow<SettingsScreenState> = _state



    companion object {
        val NAVIGATOR_KEY = object : CreationExtras.Key<Navigator> {}
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val navigator = this[NAVIGATOR_KEY] as Navigator
                SettingsViewModel(navigator)
            }
        }
    }
}