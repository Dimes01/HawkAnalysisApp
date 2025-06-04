package hawk.analysis.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import hawk.analysis.app.dto.AccountInfo
import hawk.analysis.app.dto.TokenInfo
import hawk.analysis.app.dto.UserInfo
import hawk.analysis.app.nav.Destination
import hawk.analysis.app.screens.SettingsScreenState
import hawk.analysis.app.services.AccountService
import hawk.analysis.app.services.TokenService
import hawk.analysis.app.services.UserService
import hawk.analysis.app.tiapi.InstrumentServiceTI
import hawk.analysis.app.utilities.NotSuccessfulResponseException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

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

    suspend fun updateInfo() {
        var tokenError = ""; var userError = ""; var accountsError = ""
        val tokens: List<TokenInfo> = try {
            tokenService.getAllByUserId()
        } catch (e: NotSuccessfulResponseException) {
            tokenError = e.error.details
            emptyList()
        }
        val user: UserInfo = try {
            userService.getById()
        } catch (e: NotSuccessfulResponseException) {
            userError = e.error.details
            UserInfo.default()
        }
        val accounts: List<AccountInfo> = try {
            accountService.getAllByUserId().also { localAccounts ->
                localAccounts.map { acc ->
                    if (acc.benchmarkUid == null) acc
                    else {
                        _state.value.tokens.firstOrNull()?.let { token ->
                            val ticker = instrumentServiceTI.shareByFigi(
                                token.authToken,
                                acc.benchmarkUid
                            )?.instrument?.ticker ?: acc.benchmarkUid
                            acc.copy(benchmarkUid = ticker)
                        } ?: acc
                    }
                }
            }
        } catch (e: NotSuccessfulResponseException) {
            accountsError = e.error.details
            emptyList()
        }
        _state.update { it.copy(
            tokens = tokens,
            profile = user,
            accounts = accounts,
            errorUpdateToken = tokenError,
            errorUpdateUser = userError,
            errorUpdateAccount = accountsError
        ) }
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