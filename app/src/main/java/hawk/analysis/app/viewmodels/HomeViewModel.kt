package hawk.analysis.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hawk.analysis.app.nav.Navigator
import hawk.analysis.app.screens.HomeScreenState
import hawk.analysis.app.services.AuthService
import hawk.analysis.app.services.TokenService
import hawk.analysis.app.tiapi.OperationServiceTI
import hawk.analysis.app.tiapi.UserServiceTI
import hawk.analysis.restlib.contracts.Account
import hawk.analysis.restlib.utilities.toBigDecimal
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock.System
import kotlin.math.max
import kotlin.system.measureTimeMillis

class HomeViewModel(
    private val navigator: Navigator,
    private val tokenService: TokenService,
    private val userServiceTI: UserServiceTI,
    private val operationServiceTI: OperationServiceTI,
) : ViewModel() {

    private val _accounts = mutableSetOf<Account>()
    private val _currentState = MutableStateFlow(HomeScreenState())
    val currentState: StateFlow<HomeScreenState> = _currentState

    private val _currentAccount = MutableStateFlow<Account?>(null)
    val currentAccount: StateFlow<Account?> = _currentAccount.asStateFlow()

    private var updateJob: Job? = null

    init {
        startPeriodicUpdates()
    }

    private fun startPeriodicUpdates() {
        updateJob = viewModelScope.launch {
            while (true) {
                val executionTime = measureTimeMillis { updateAccounts() }
                val lastTime = UPDATE_INTERVAL - executionTime
                val delayTime = max(0, lastTime)
                delay(delayTime)
            }
        }
    }

    fun updateAccounts() = viewModelScope.launch {
        val tokens = tokenService.getAllByUserId(AuthService.jwt)
        val allAccounts = tokens.flatMap { token ->
            userServiceTI.getAccounts(token.authToken).accounts
        }.toSet()

        _accounts.clear()
        _accounts.addAll(allAccounts)

        if (_currentAccount.value !in _accounts || _accounts.isEmpty()) {
            _currentAccount.value = _accounts.firstOrNull()
        }

        _currentAccount.value?.let { currentAccount ->
            updatePortfolioInfo(currentAccount)
        }

        _currentState.update {
            it.copy(
                lastUpdatedAt = System.now(),
            )
        }
    }

    private suspend fun updatePortfolioInfo(account: Account) {
        var wasUpdated = false
        tokenService.getAllByUserId(AuthService.jwt).forEach { token ->
            println(token)
            operationServiceTI.getPortfolio(token.authToken, account.id)?.let { portfolio ->
                println(portfolio)
                if (!wasUpdated) _currentState.update {
                    wasUpdated = true
                    it.copy(
                        sum = portfolio.totalAmountPortfolio.toBigDecimal(),
                        profit = portfolio.dailyYield.toBigDecimal(),
                        profitRelative = portfolio.dailyYieldRelative.toBigDecimal()
                    )
                }
            }
        }
    }

    fun selectPreviousAccount() {
        val accounts = _accounts.toList()
        if (accounts.isEmpty()) return

        val current = _currentAccount.value
        val currentIndex = accounts.indexOf(current)
        val newIndex = if (currentIndex <= 0) accounts.lastIndex else currentIndex - 1
        _currentAccount.value = accounts[newIndex]
    }

    fun selectNextAccount() {
        val accounts = _accounts.toList()
        if (accounts.isEmpty()) return

        val current = _currentAccount.value
        val currentIndex = accounts.indexOf(current)
        val newIndex = if (currentIndex >= accounts.lastIndex) 0 else currentIndex + 1
        _currentAccount.value = accounts[newIndex]
    }

    override fun onCleared() {
        super.onCleared()
        updateJob?.cancel()
    }

    companion object {
        private const val UPDATE_INTERVAL = 5000L

        val NAVIGATOR_KEY = object : CreationExtras.Key<Navigator> {}
        val USER_SERVICE_TI_KEY = object : CreationExtras.Key<UserServiceTI> {}
        val TOKEN_SERVICE_KEY = object : CreationExtras.Key<TokenService> {}
        val OPERATION_SERVICE_TI_KEY = object : CreationExtras.Key<OperationServiceTI> {}

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                HomeViewModel(
                    navigator = this[NAVIGATOR_KEY] as Navigator,
                    tokenService = this[TOKEN_SERVICE_KEY] as TokenService,
                    userServiceTI = this[USER_SERVICE_TI_KEY] as UserServiceTI,
                    operationServiceTI = this[OPERATION_SERVICE_TI_KEY] as OperationServiceTI
                )
            }
        }
    }
}
