package hawk.analysis.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import hawk.analysis.app.nav.Destination
import hawk.analysis.app.screens.SettingsScreenState
import hawk.analysis.app.services.AccountService
import hawk.analysis.app.services.TokenService
import hawk.analysis.app.services.UserService
import hawk.analysis.app.tiapi.InstrumentServiceTI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val navController: NavController,
    private val userService: UserService,
    private val accountService: AccountService,
    private val tokenService: TokenService,
    private val instrumentServiceTI: InstrumentServiceTI,
    private val sharedViewModel: SharedViewModel,
) : ViewModel()  {
    private val _state = MutableStateFlow(SettingsScreenState())
    val state: StateFlow<SettingsScreenState> = _state

    init {
        viewModelScope.launch {
            updateInfo()
        }
    }

    private suspend fun updateInfo() {
        userService.getById()?.also { user -> _state.update { it.copy(profile = user) } }
        accountService.getAllByUserId()?.also { acc -> _state.update { it.copy(accounts = acc) } }
        tokenService.getAllByUserId()?.also { token -> _state.update { it.copy(tokens = token) } }
    }

    suspend fun actGetTickerByFigi(figi: String): String? = state.value.tokens.firstOrNull()?.authToken?.let {
        instrumentServiceTI.shareByFigi(it, figi)?.instrument?.ticker
    }

    fun navToEditEmail() {
        navController.navigate(Destination.EditEmailScreen)
    }

    fun navToEditPassword() {
        navController.navigate(Destination.EditPasswordScreen)
    }

    fun navToEditAccount(accountId: String) {
        sharedViewModel.getTokenByAccountId(accountId)?.let {
            navController.navigate(Destination.EditAccountScreen(it.authToken, accountId))
        }
    }

    fun navToEditToken(tokenId: Int) {
        navController.navigate(Destination.EditAuthTokenScreen(tokenId))
    }

    fun navToAddToken() {
        navController.navigate(Destination.AddAuthTokenScreen)
    }

    companion object {
        val NAV_CONTROLLER = object : CreationExtras.Key<NavController> {}
        val USER_SERVICE_KEY = object : CreationExtras.Key<UserService> {}
        val ACCOUNT_SERVICE_KEY = object : CreationExtras.Key<AccountService> {}
        val TOKEN_SERVICE_KEY = object : CreationExtras.Key<TokenService> {}
        val INSTRUMENT_SERVICE_TI_KEY = object : CreationExtras.Key<InstrumentServiceTI> {}
        val SHARED_VIEW_MODEL_KEY = object : CreationExtras.Key<SharedViewModel> {}

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val navController = this[NAV_CONTROLLER] as NavController
                val userService = this[USER_SERVICE_KEY] as UserService
                val accountService = this[ACCOUNT_SERVICE_KEY] as AccountService
                val tokenService = this[TOKEN_SERVICE_KEY] as TokenService
                val instrumentServiceTI = this[INSTRUMENT_SERVICE_TI_KEY] as InstrumentServiceTI
                val sharedViewModel = this[SHARED_VIEW_MODEL_KEY] as SharedViewModel
                SettingsViewModel(navController, userService, accountService, tokenService, instrumentServiceTI, sharedViewModel)
            }
        }
    }
}