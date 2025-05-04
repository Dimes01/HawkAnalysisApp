package hawk.analysis.app.viewmodels

import androidx.compose.runtime.State
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
import hawk.analysis.app.tiapi.UserServiceTI
import hawk.analysis.restlib.models.Account
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Clock.System
import kotlinx.datetime.Instant

class HomeViewModel(
    private val navigator: Navigator,
    private val userServiceTI: UserServiceTI,
    private val tokenService: TokenService,
) : ViewModel() {
    private var _accounts = mutableStateListOf<Account>()
    private var index = 0

    private var _selectedAccount = MutableStateFlow<Account?>(null)
    val selectedAccount: StateFlow<Account?> = _selectedAccount.asStateFlow()

    private var _lastUpdatedAt = mutableStateOf<Instant>(System.now())
    val lastUpdatedAt: State<Instant> = _lastUpdatedAt

    fun updateAccounts() = viewModelScope.launch {
        println("Updating accounts")
        val tokens = tokenService.getAllByUserId(AuthService.jwt)
        println("Getting ${tokens.size} tokens")
        val allAccounts = mutableSetOf<Account>()
        for (token in tokens) {
            userServiceTI.getAccounts(token.authToken)?.apply { allAccounts.addAll(this.accounts) }
        }
        _accounts.clear()
        _accounts.addAll(allAccounts)
        _lastUpdatedAt.value = System.now()
        changeSelectedAccount()
        println("Getting ${_accounts.size} tokens")
    }

    private fun changeSelectedAccount() = viewModelScope.launch {
        println("Updating selected account")
        if (index >= 0 && index < _accounts.count()) {
            println("Index of selected account: $index")
            _selectedAccount.value = _accounts[index]
        }
    }

    fun previousAccount() {
        if (index > 0) {
            --index
            changeSelectedAccount()
        }
    }

    fun nextAccount() {
        if (index < _accounts.size - 1) {
            ++index
            changeSelectedAccount()
        }
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
        val USER_SERVICE_TI_KEY = object : CreationExtras.Key<UserServiceTI> {}
        val TOKEN_SERVICE_KEY = object : CreationExtras.Key<TokenService> {}
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val navigator = this[NAVIGATOR_KEY] as Navigator
                val userServiceTI = this[USER_SERVICE_TI_KEY] as UserServiceTI
                val tokenService = this[TOKEN_SERVICE_KEY] as TokenService
                HomeViewModel(navigator, userServiceTI, tokenService)
            }
        }
    }
}
