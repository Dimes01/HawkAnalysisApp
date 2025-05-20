package hawk.analysis.app.viewmodels

import android.icu.math.BigDecimal
import android.icu.math.MathContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import hawk.analysis.app.dto.TokenInfo
import hawk.analysis.app.screens.HomeScreenState
import hawk.analysis.app.screens.MoneyState
import hawk.analysis.app.screens.ShareState
import hawk.analysis.app.services.TokenService
import hawk.analysis.app.tiapi.InstrumentServiceTI
import hawk.analysis.app.tiapi.OperationServiceTI
import hawk.analysis.app.tiapi.UserServiceTI
import hawk.analysis.restlib.contracts.Account
import hawk.analysis.restlib.contracts.MoneyValue
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
    private val navController: NavController,
    private val tokenService: TokenService,
    private val userServiceTI: UserServiceTI,
    private val operationServiceTI: OperationServiceTI,
    private val instrumentsServiceTI: InstrumentServiceTI,
) : ViewModel() {
    private val accountsToToken: MutableList<Pair<Account, TokenInfo>> = mutableListOf()

    private val _currentState = MutableStateFlow(HomeScreenState())
    val currentState: StateFlow<HomeScreenState> = _currentState

    private val _currentAccount = MutableStateFlow<Account?>(null)
    val currentAccount: StateFlow<Account?> = _currentAccount.asStateFlow()

    private var updateJob: Job? = null

    init {
        updateAccounts()
        startPeriodicUpdates()
    }

    private fun startPeriodicUpdates() {
        updateJob = viewModelScope.launch {
            while (true) {
                val executionTime = measureTimeMillis {
                    _currentAccount.value?.let { currentAccount ->
                        updatePortfolioInfo(accountsToToken.first { it.first == currentAccount })
                    }

                    _currentState.update {
                        it.copy(
                            lastUpdatedAt = System.now(),
                        )
                    }
                }
                val lastTime = UPDATE_INTERVAL - executionTime
                val delayTime = max(0, lastTime)
                delay(delayTime)
            }
        }
    }

    fun updateAccounts() = viewModelScope.launch {
        val tokens = tokenService.getAllByUserId() ?: emptyList()
        val set = HashSet<Pair<Account, TokenInfo>>()
        for (token in tokens) {
            userServiceTI.getAccounts(token.authToken).accounts.forEach { acc ->
                set.add(acc to token)
            }
        }
        accountsToToken.clear()
        accountsToToken.addAll(set)
        if (accountsToToken.isEmpty()) _currentAccount.value = null
        else _currentAccount.value = accountsToToken.first().first
    }

    private suspend fun updatePortfolioInfo(pair: Pair<Account, TokenInfo>) {
        val acc = pair.first
        val token = pair.second
        operationServiceTI.getPortfolio(token.authToken, acc.id)?.let { portfolio ->
            val moneyStates = portfolio.positions.filter { it.instrumentType == "currency" }.mapNotNull { portCur ->
                val cur = instrumentsServiceTI.currencyByFigi(token.authToken, portCur.figi)?.instrument
                if (cur != null) {
                    val money = MoneyValue(portCur.averagePositionPrice.currency, portCur.quantity.units, portCur.quantity.nano)
                    MoneyState(cur, money)
                } else null
            }
            println(moneyStates)
            val shareStates = portfolio.positions.filter { it.instrumentType == "share" }.mapNotNull { portShare ->
                val share = instrumentsServiceTI.shareByFigi(token.authToken, portShare.figi)?.instrument
                if (share != null) {
                    val currentPrice = portShare.currentPrice.toBigDecimal()
                    val quantity = BigDecimal(portShare.quantity.units)
                    val profit = portShare.expectedYieldFifo.toBigDecimal()
                    val profitPercent = profit.divide(portShare.averagePositionPriceFifo.toBigDecimal(), 2, MathContext.ROUND_HALF_UP).multiply(BigDecimal(100))
                    ShareState(
                        name = share.name,
                        sum = currentPrice.multiply(quantity).setScale(2, MathContext.ROUND_HALF_UP),
                        profit = profit,
                        profitPercent = profitPercent,
                        count = quantity.toInt(),
                        countOfLots = quantity.divide(BigDecimal(share.lot), 2, MathContext.ROUND_HALF_UP).toInt(),
                        currencyCode = portShare.currentPrice.currency
                    )
                } else null
            }
            println(shareStates)
            _currentState.update { it.copy(
                lastUpdatedAt = System.now(),
                sum = portfolio.totalAmountPortfolio.toBigDecimal(),
                profit = portfolio.dailyYield.toBigDecimal(),
                profitRelative = portfolio.dailyYieldRelative.toBigDecimal(),
                money = moneyStates,
                shares = shareStates
            ) }
        }
    }

    fun selectPreviousAccount() {
        val accounts = accountsToToken.map { it.first }
        if (accounts.isEmpty()) return

        val current = _currentAccount.value
        val currentIndex = accounts.indexOf(current)
        val newIndex = if (currentIndex <= 0) accounts.lastIndex else currentIndex - 1
        _currentAccount.value = accounts[newIndex]
    }

    fun selectNextAccount() {
        val accounts = accountsToToken.map { it.first }
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

        val NAV_CONTROLLER = object : CreationExtras.Key<NavController> {}
        val USER_SERVICE_TI_KEY = object : CreationExtras.Key<UserServiceTI> {}
        val TOKEN_SERVICE_KEY = object : CreationExtras.Key<TokenService> {}
        val OPERATION_SERVICE_TI_KEY = object : CreationExtras.Key<OperationServiceTI> {}
        val INSTRUMENT_SERVICE_TI_KEY = object : CreationExtras.Key<InstrumentServiceTI> {}

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                HomeViewModel(
                    navController = this[NAV_CONTROLLER] as NavController,
                    tokenService = this[TOKEN_SERVICE_KEY] as TokenService,
                    userServiceTI = this[USER_SERVICE_TI_KEY] as UserServiceTI,
                    operationServiceTI = this[OPERATION_SERVICE_TI_KEY] as OperationServiceTI,
                    instrumentsServiceTI = this[INSTRUMENT_SERVICE_TI_KEY] as InstrumentServiceTI
                )
            }
        }
    }
}
