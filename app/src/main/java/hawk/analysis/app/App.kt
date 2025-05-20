package hawk.analysis.app

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import hawk.analysis.app.di.commonModule
import hawk.analysis.app.di.devModule
import hawk.analysis.app.nav.BottomNavigationBar
import hawk.analysis.app.nav.Destination
import hawk.analysis.app.screens.AccountVM
import hawk.analysis.app.screens.AddAuthToken
import hawk.analysis.app.screens.Asset
import hawk.analysis.app.screens.EditAccount
import hawk.analysis.app.screens.EditAuthToken
import hawk.analysis.app.screens.EditEmail
import hawk.analysis.app.screens.EditPassword
import hawk.analysis.app.screens.HomeVM
import hawk.analysis.app.screens.Login
import hawk.analysis.app.screens.Register
import hawk.analysis.app.screens.SettingsVM
import hawk.analysis.app.services.AccountService
import hawk.analysis.app.services.AnalyseService
import hawk.analysis.app.services.TokenService
import hawk.analysis.app.services.UserService
import hawk.analysis.app.tiapi.InstrumentServiceTI
import hawk.analysis.app.tiapi.OperationServiceTI
import hawk.analysis.app.tiapi.UserServiceTI
import hawk.analysis.app.ui.theme.HawkAnalysisAppTheme
import hawk.analysis.app.viewmodels.AccountViewModel
import hawk.analysis.app.viewmodels.HomeViewModel
import hawk.analysis.app.viewmodels.SettingsViewModel
import hawk.analysis.restlib.contracts.PortfolioResponse
import hawk.analysis.restlib.contracts.Share
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.compose.koinInject
import org.koin.core.context.startKoin

/**
 * Навигация, написанная на основе следующего примера с GitHub и YouTube, работала плохо, но использовать можно:
 * - [philipplackner/NavigationFromViewModel](https://github.com/philipplackner/NavigationFromViewModel/tree/master)
 * - [(156) How to Navigate From ViewModels With a Custom Navigator - Android Studio Tutorial - YouTube](https://www.youtube.com/watch?v=BFhVvAzC52w&t=579s)
 *
 * Полезная информация:
 * - [Create ViewModels with dependencies  |  App architecture  |  Android Developers](https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-factories#jetpack-compose_1)
 * - [Pass data between destinations  |  App architecture  |  Android Developers](https://developer.android.com/guide/navigation/use-graph/pass-data)
 */


class HawkApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@HawkApp)
            modules(commonModule, devModule)
        }
    }
}

@Composable
fun App() {
    HawkAnalysisAppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
        ) { innerPadding ->
            val navController = rememberNavController()

            Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                var navBarVisible by remember { mutableStateOf(true) }
                NavHost(
                    navController = navController,
                    startDestination = Destination.AuthGraph,
                    modifier = Modifier.align(Alignment.TopCenter)

                ) {
                    navigation<Destination.AuthGraph>(startDestination = Destination.LoginScreen) {
                        composable<Destination.LoginScreen> {
                            navBarVisible = false
                            Login(
                                onHomeScreen = { navController.navigate(Destination.HomeScreen) },
                                onRegisterScreen = { navController.navigate(Destination.RegisterScreen) },
                            )
                        }
                        composable<Destination.RegisterScreen> {
                            navBarVisible = false
                            Register (
                                onHomeScreen = { navController.navigate(Destination.HomeScreen) },
                                onLoginScreen = { navController.navigate(Destination.LoginScreen) },
                            )
                        }
                    }
                    navigation<Destination.HomeGraph>(startDestination = Destination.HomeScreen) {
                        composable<Destination.HomeScreen> {
                            navBarVisible = true
                            val tokenService = koinInject<TokenService>()
                            val userServiceTI = koinInject<UserServiceTI>()
                            val operationServiceTI = koinInject<OperationServiceTI>()
                            val instrumentServiceTI = koinInject<InstrumentServiceTI>()
                            val extras = MutableCreationExtras().apply {
                                set(HomeViewModel.NAV_CONTROLLER, navController)
                                set(HomeViewModel.TOKEN_SERVICE_KEY, tokenService)
                                set(HomeViewModel.USER_SERVICE_TI_KEY, userServiceTI)
                                set(HomeViewModel.OPERATION_SERVICE_TI_KEY, operationServiceTI)
                                set(HomeViewModel.INSTRUMENT_SERVICE_TI_KEY, instrumentServiceTI)
                            }
                            val viewModel = viewModel<HomeViewModel>(factory = HomeViewModel.Factory, extras = extras)
                            HomeVM(viewModel)
                        }
                        composable<Destination.AccountScreen> {
                            navBarVisible = true
                            val extras = MutableCreationExtras().apply { set(AccountViewModel.NAV_CONTROLLER, navController) }
                            val viewModel = viewModel<AccountViewModel>(factory = AccountViewModel.Factory, extras = extras)
                            AccountVM(viewModel)
                        }
                        composable<Destination.AssetScreen> {
                            navBarVisible = false
                            val instrumentServiceTI = koinInject<InstrumentServiceTI>()
                            val operationServiceTI = koinInject<OperationServiceTI>()
                            val analyseService = koinInject<AnalyseService>()
                            val args = it.toRoute<Destination.AssetScreen>()
                            val getInfo: suspend (authToken: String, figi: String) -> Share? = { a, f ->
                                instrumentServiceTI.shareByFigi(a, f)?.instrument
                            }
                            val getPortfolio: suspend (authToken: String, accountId: String) -> PortfolioResponse? = { at, aid ->
                                operationServiceTI.getPortfolio(at, aid)
                            }
                            Asset(
                                args.uid,
                                args.authToken,
                                args.accountId,
                                getInfo,
                                getPortfolio,
                                analyseService::getLast,
                                modifier = Modifier.verticalScroll(rememberScrollState())
                            )
                        }
                    }
                    navigation<Destination.SettingsGraph>(startDestination = Destination.SettingsScreen) {
                        composable<Destination.SettingsScreen> {
                            navBarVisible = true
                            val userService = koinInject<UserService>()
                            val accountService = koinInject<AccountService>()
                            val tokenService = koinInject<TokenService>()
                            val extras = MutableCreationExtras().apply {
                                set(SettingsViewModel.NAV_CONTROLLER, navController)
                                set(SettingsViewModel.USER_SERVICE_KEY, userService)
                                set(SettingsViewModel.ACCOUNT_SERVICE_KEY, accountService)
                                set(SettingsViewModel.TOKEN_SERVICE_KEY, tokenService)
                            }
                            val viewModel = viewModel<SettingsViewModel>(factory = SettingsViewModel.Factory, extras = extras)
                            SettingsVM(viewModel)
                        }
                        composable<Destination.EditEmailScreen> {
                            navBarVisible = false
                            val userService = koinInject<UserService>()
                            EditEmail(
                                actSave = userService::updateEmail,
                                navToBack = navController::navigateUp
                            )
                        }
                        composable<Destination.EditPasswordScreen> {
                            navBarVisible = false
                            val userService = koinInject<UserService>()
                            EditPassword(
                                actSave = userService::updatePassword,
                                navToBack = navController::navigateUp
                            )
                        }
                        composable<Destination.EditAccountScreen> {
                            navBarVisible = false
                            val accountService = koinInject<AccountService>()
                            val args = it.toRoute<Destination.EditAccountScreen>()
                            EditAccount(
                                accountId = args.accountId,
                                actChangeRiskFree = accountService::changeRiskFree,
                                actChangeBenchmark = accountService::changeBenchmark,
                                navToBack = navController::navigateUp
                            )
                        }
                        composable<Destination.AddAuthTokenScreen> {
                            navBarVisible = false
                            val tokenService = koinInject<TokenService>()
                            AddAuthToken(
                                actSave = tokenService::create,
                                navToBack = navController::navigateUp
                            )
                        }
                        composable<Destination.EditAuthTokenScreen> {
                            navBarVisible = false
                            val tokenService = koinInject<TokenService>()
                            val args = it.toRoute<Destination.EditAuthTokenScreen>()
                            EditAuthToken(
                                tokenId = args.tokenId,
                                actSave = tokenService::update,
                                navToBack = navController::navigateUp
                            )
                        }
                    }
                }
                BottomNavigationBar(navController, navBarVisible, Modifier.align(Alignment.BottomCenter))
            }
        }
    }
}