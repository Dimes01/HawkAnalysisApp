package hawk.analysis.app.viewmodels

import android.icu.math.BigDecimal
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import hawk.analysis.app.Destination
import hawk.analysis.app.SettingsScreen
import hawk.analysis.app.dto.Account
import hawk.analysis.app.dto.User
import hawk.analysis.app.nav.Navigator
import hawk.analysis.app.utilities.Provider
import kotlinx.coroutines.launch

class HomeViewModel(
    private val navigator: Navigator,
) : ViewModel() {
    var user = User(Provider.name, Provider.age)
    var account = Account(user, BigDecimal(100.0))

    fun toSettings() {
        viewModelScope.launch {
            navigator.navigate(destination = SettingsScreen(user.age, account.user.name))
        }
    }
}

class HomeViewModelFactory(private val navigator: Navigator) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(navigator) as T
    }
}