package hawk.analysis.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hawk.analysis.app.nav.Navigator

class AccountViewModel(
    private val navigator: Navigator,
) : ViewModel() {
    companion object {
        val NAVIGATOR_KEY = object : CreationExtras.Key<Navigator> {}
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val navigator = this[NAVIGATOR_KEY] as Navigator
                AccountViewModel(navigator)
            }
        }
    }


}