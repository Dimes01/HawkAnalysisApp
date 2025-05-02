package hawk.analysis.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hawk.analysis.app.nav.Navigator

class AccountViewModel(
    private val navigator: Navigator,
    private val accountId: String,
) : ViewModel() {
    companion object {
        val NAVIGATOR_KEY = object : CreationExtras.Key<Navigator> {}
        val ACCOUNT_ID_KEY = object : CreationExtras.Key<String> {}
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val navigator = this[NAVIGATOR_KEY] as Navigator
                val accountId = this[ACCOUNT_ID_KEY] as String
                AccountViewModel(navigator, accountId)
            }
        }
    }


}