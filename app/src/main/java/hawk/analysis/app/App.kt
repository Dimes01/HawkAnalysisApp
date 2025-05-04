package hawk.analysis.app

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import hawk.analysis.app.di.commonModule
import hawk.analysis.app.di.devModule
import hawk.analysis.app.nav.BottomNavigationBar
import hawk.analysis.app.nav.DefaultNavigator
import hawk.analysis.app.nav.Destination
import hawk.analysis.app.nav.NavigationAction
import hawk.analysis.app.nav.ObserveAsEvents
import hawk.analysis.app.screens.AccountVM
import hawk.analysis.app.screens.HomeVM
import hawk.analysis.app.screens.Login
import hawk.analysis.app.screens.Register
import hawk.analysis.app.screens.SettingsVM
import hawk.analysis.app.services.TokenService
import hawk.analysis.app.ui.theme.HawkAnalysisAppTheme
import hawk.analysis.app.viewmodels.AccountViewModel
import hawk.analysis.app.viewmodels.HomeViewModel
import hawk.analysis.app.viewmodels.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.compose.koinInject
import org.koin.core.context.startKoin
import java.security.Security

/**
 * Навигация написана на основе следующего примера с GitHub и YouTube:
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
//        Security.insertProviderAt(Conscrypt.newProvider(), 1)
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
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val navController = rememberNavController()
            val navigator = DefaultNavigator(Destination.AuthGraph)

            ObserveAsEvents(flow = navigator.navigationActions) { action ->
                when(action) {
                    is NavigationAction.Navigate -> navController.navigate(
                        action.destination
                    ) {
                        action.navOptions(this)
                    }
                    NavigationAction.NavigateUp -> navController.navigateUp()
                }
            }

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                var navBarVisible by remember { mutableStateOf(true) }
                NavHost(
                    navController = navController,
                    startDestination = navigator.startDestination,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(innerPadding)
                ) {
                    navigation<Destination.AuthGraph>(
                        startDestination = Destination.LoginScreen
                    ) {
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
                    navigation<Destination.HomeGraph>(
                        startDestination = Destination.HomeScreen
                    ) {
                        composable<Destination.HomeScreen> {
                            navBarVisible = true
                            val tokenService = koinInject<TokenService>()
                            val extras = MutableCreationExtras().apply {
                                set(HomeViewModel.NAVIGATOR_KEY, navigator)
                                set(HomeViewModel.TOKEN_SERVICE_KEY, tokenService)
                            }
                            val viewModel = viewModel<HomeViewModel>(factory = HomeViewModel.Factory, extras = extras)
                            HomeVM(viewModel)
                        }
                        composable<Destination.SettingsScreen> {
                            navBarVisible = true
                            val extras = MutableCreationExtras().apply { set(SettingsViewModel.NAVIGATOR_KEY, navigator) }
                            val viewModel = viewModel<SettingsViewModel>(factory = SettingsViewModel.Factory, extras = extras)
                            SettingsVM(viewModel)
                        }
                        composable<Destination.AccountScreen> {
                            navBarVisible = true
                            val extras = MutableCreationExtras().apply { set(AccountViewModel.NAVIGATOR_KEY, navigator) }
                            val viewModel = viewModel<AccountViewModel>(factory = AccountViewModel.Factory, extras = extras)
                            AccountVM(viewModel)
                        }
                    }
                }
                BottomNavigationBar(navController, navBarVisible, Modifier.align(Alignment.BottomCenter))
            }
        }
    }
}