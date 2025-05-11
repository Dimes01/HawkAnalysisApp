package hawk.analysis.app.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hawk.analysis.app.dto.TokenInfo
import hawk.analysis.app.nav.Navigator
import hawk.analysis.app.screens.HomeScreenState
import hawk.analysis.app.services.AuthService
import hawk.analysis.app.services.TokenService
import hawk.analysis.app.tiapi.OperationServiceTI
import hawk.analysis.app.tiapi.UserServiceTI
import hawk.analysis.restlib.contracts.Account
import hawk.analysis.restlib.utilities.toBigDecimal
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
    // Охренительная логика:
    // Создать множество троек <аккаунт, токен, состояние>.
    // Когда нужно обновить информацию, проходим по множеству и получаем информацию.
    // Когда происходит смена аккаунта, то находим в множестве новый аккаунт и изменяем состояние на то, что есть в тройке

    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state

    private val _accounts = mutableStateListOf<Account>()
    private val _tokens = mutableStateListOf<TokenInfo>()
    private var index = 0

    private var _selectedAccount = MutableStateFlow<Account?>(null)
    val selectedAccount: StateFlow<Account?> = _selectedAccount.asStateFlow()

    init {
        startSchedulingUpdate()
    }

    private fun startSchedulingUpdate() {
        viewModelScope.launch {
            while (true) {
                val executionTime = measureTimeMillis { updateAccounts() }
                val lastDelay = max(0, 5000 - executionTime)
                delay(lastDelay)
            }
        }
    }

    fun updateAccounts() = viewModelScope.launch {
        println("Updating accounts")
        val tokens = tokenService.getAllByUserId(AuthService.jwt).also {
            println("Getting ${it.size} tokens")
        }
        val allAccounts = tokens.flatMap { token ->
            userServiceTI.getAccounts(token.authToken).accounts
        }.toSet()
        _accounts.run {
            clear()
            addAll(allAccounts)
        }
        _state.update { it.copy(lastUpdatedAt = System.now()) }

        changeSelectedAccount()

        selectedAccount.value?.let { acc ->
            _tokens.forEach { token ->
                operationServiceTI.getPortfolio(token.authToken, acc.id)?.let { portfolio ->
                    _state.update { it.copy(sum = portfolio.totalAmountPortfolio.toBigDecimal()) }
                    _state.update { it.copy(profit = portfolio.dailyYield.toBigDecimal()) }
                    _state.update { it.copy(profitRelative = portfolio.dailyYieldRelative.toBigDecimal()) }
                }
            }
        }
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

    companion object {
        val NAVIGATOR_KEY = object : CreationExtras.Key<Navigator> {}
        val USER_SERVICE_TI_KEY = object : CreationExtras.Key<UserServiceTI> {}
        val TOKEN_SERVICE_KEY = object : CreationExtras.Key<TokenService> {}
        val OPERATION_SERVICE_TI_KEY = object : CreationExtras.Key<OperationServiceTI> {}
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val navigator = this[NAVIGATOR_KEY] as Navigator
                val tokenService = this[TOKEN_SERVICE_KEY] as TokenService
                val userServiceTI = this[USER_SERVICE_TI_KEY] as UserServiceTI
                val operationServiceTI = this[OPERATION_SERVICE_TI_KEY] as OperationServiceTI
                HomeViewModel(navigator, tokenService, userServiceTI, operationServiceTI)
            }
        }
    }
}
