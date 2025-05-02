package hawk.analysis.app

import android.app.Application
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import hawk.analysis.app.di.appModule
import hawk.analysis.app.nav.DefaultNavigator
import hawk.analysis.app.nav.Destination
import hawk.analysis.app.nav.NavigationAction
import hawk.analysis.app.nav.ObserveAsEvents
import hawk.analysis.app.screens.Home
import hawk.analysis.app.screens.Login
import hawk.analysis.app.screens.Register
import hawk.analysis.app.screens.Settings
import hawk.analysis.app.ui.theme.HawkAnalysisAppTheme
import hawk.analysis.app.viewmodels.HomeViewModel
import hawk.analysis.app.viewmodels.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

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
        startKoin {
            androidLogger()
            androidContext(this@HawkApp)
            modules(appModule)
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

            NavHost(
                navController = navController,
                startDestination = Destination.AuthGraph,
                modifier = Modifier.padding(innerPadding)
            ) {
                navigation<Destination.AuthGraph>(
                    startDestination = Destination.LoginScreen
                ) {
                    composable<Destination.LoginScreen> {
                        Login(
                            onHomeScreen = { suspend { navigator.navigate(Destination.HomeScreen) {} } },
                            onRegisterScreen = { suspend { navigator.navigate(Destination.RegisterScreen) {} } },
                        )
                    }
                    composable<Destination.RegisterScreen> {
                        Register (
                            onHomeScreen = { suspend { navigator.navigate(Destination.HomeScreen) {} } },
                            onLoginScreen = { suspend { navigator.navigate(Destination.LoginScreen) {} } },
                        )
                    }
                }
                navigation<Destination.HomeGraph>(
                    startDestination = Destination.HomeScreen
                ) {
                    composable<Destination.HomeScreen> {
                        val extras = MutableCreationExtras().apply { set(HomeViewModel.NAVIGATOR_KEY, navigator) }
                        val viewModel = viewModel<HomeViewModel>(factory = HomeViewModel.Factory, extras = extras)
                        Home(viewModel)
                    }
                    composable<Destination.SettingsScreen> {
                        val extras = MutableCreationExtras().apply { set(SettingsViewModel.NAVIGATOR_KEY, navigator) }
                        val viewModel = viewModel<SettingsViewModel>(factory = SettingsViewModel.Factory, extras = extras)
                        Settings(viewModel)
                    }
                }
            }
        }
    }
}