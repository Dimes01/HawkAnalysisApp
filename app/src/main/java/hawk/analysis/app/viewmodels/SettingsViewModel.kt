package hawk.analysis.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hawk.analysis.app.dto.Account
import hawk.analysis.app.dto.User
import hawk.analysis.app.nav.Navigator
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class SettingsViewModel(
    private val navigator: Navigator,
    private val user: User,
    private val account: Account
) : ViewModel()  {
    companion object {
        val NAVIGATOR_KEY = object : CreationExtras.Key<Navigator> {}
        val USER_KEY = object : CreationExtras.Key<User> {}
        val ACCOUNT_KEY = object : CreationExtras.Key<Account> {}

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val navigator = this[NAVIGATOR_KEY] as Navigator
                val user = this[USER_KEY] as User
                val account = this[ACCOUNT_KEY] as Account
                SettingsViewModel(navigator, user, account)
            }
        }
    }

    fun toMain() {
        viewModelScope.launch {
            navigator.navigateUp()
        }
    }
}

class SettingsViewModelFactory(
    private val navigator: Navigator,
    private val user: User,
    private val account: Account
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(navigator, user, account) as T
    }
}