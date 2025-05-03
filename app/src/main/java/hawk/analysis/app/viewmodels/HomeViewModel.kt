package hawk.analysis.app.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hawk.analysis.app.nav.Destination
import hawk.analysis.app.nav.Navigator
import hawk.analysis.app.services.AuthService
import hawk.analysis.app.services.TokenService
import hawk.analysis.app.tiapi.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.tinkoff.piapi.contract.v1.Account
import ru.tinkoff.piapi.core.stream.MarketDataStreamService

class HomeViewModel(
    private val navigator: Navigator,
    private val tokenService: TokenService
) : ViewModel() {
    private lateinit var marketDataStreamService: MarketDataStreamService
    private var _accounts = mutableStateListOf<Account>()

    private var _selectedAccount = MutableStateFlow<Account?>(null)
    val selectedAccount: StateFlow<Account?> = _selectedAccount.asStateFlow()

    init {
        println("Initiate HomeViewModel")
        updateAccounts()
        changeSelectedAccount(0)
    }

    private fun updateAccounts() = viewModelScope.launch {
        println("Updating accounts")
        val tokens = tokenService.getAllByUserId(AuthService.jwt)
        val allAccounts = mutableSetOf<Account>()
        for (token in tokens) {
            val service = UserService(token.authToken)
            service.getAccounts()?.apply { allAccounts.addAll(this) }
        }
        _accounts.clear()
        _accounts.addAll(allAccounts)
    }

    private fun changeSelectedAccount(index: Int) = viewModelScope.launch {
        println("Updating selected account with index: $index")
        if (index >= 0 && index < _accounts.count())
            _selectedAccount.value = _accounts[index]
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

    companion object {
        val NAVIGATOR_KEY = object : CreationExtras.Key<Navigator> {}
        val TOKEN_SERVICE_KEY = object : CreationExtras.Key<TokenService> {}
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val navigator = this[NAVIGATOR_KEY] as Navigator
                val tokenService = this[TOKEN_SERVICE_KEY] as TokenService
                HomeViewModel(navigator, tokenService)
            }
        }
    }
}
