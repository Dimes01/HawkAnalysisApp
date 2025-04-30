package hawk.analysis.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import hawk.analysis.app.dto.Account
import hawk.analysis.app.dto.User
import hawk.analysis.app.nav.DefaultNavigator
import hawk.analysis.app.nav.NavigationAction
import hawk.analysis.app.nav.ObserveAsEvents
import hawk.analysis.app.screens.Login
import hawk.analysis.app.ui.theme.HawkAnalysisAppTheme
import kotlinx.serialization.Serializable

sealed interface Destination

@Serializable
object AuthGraph

@Serializable
data object HomeGraph : Destination

@Serializable
object LoginScreen

@Serializable
data object RegisterScreen : Destination

@Serializable
data object HomeScreen : Destination

@Serializable
data class SettingsScreen(val userId: Int, val accountId: String) : Destination

@Composable
fun App() {
    HawkAnalysisAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val navController = rememberNavController()
//            val navigator = DefaultNavigator(LoginScreen)
//
//            ObserveAsEvents(flow = navigator.navigationActions) { action ->
//                when(action) {
//                    is NavigationAction.Navigate -> navController.navigate(
//                        action.destination
//                    ) {
//                        action.navOptions(this)
//                    }
//                    NavigationAction.NavigateUp -> navController.navigateUp()
//                }
//            }

            NavHost(
                navController = navController,
                startDestination = LoginScreen,
                modifier = Modifier.padding(innerPadding)
            ) {
                navigation<AuthGraph>(
                    startDestination = LoginScreen
                ) {
                    composable<LoginScreen> {
//                                val viewModel = viewModel<LoginViewModel>(factory = LoginViewModelFactory(navigator))
//                                Login(viewModel)
                        Login()
                    }
                }
//                        navigation<Destination.HomeGraph>(
//                            startDestination = Destination.HomeScreen
//                        ) {
//                            composable<Destination.HomeScreen> {
//                                val viewModel = viewModel<HomeViewModel>(factory = HomeViewModelFactory(navigator))
//                                Home(viewModel)
//                            }
//                            composable<Destination.SettingsScreen> {
//                                val args = it.toRoute<Destination.SettingsScreen>()
//                                val viewModel = viewModel<SettingsViewModel>(
//                                    factory = SettingsViewModelFactory(navigator, args.user, args.account)
//                                )
//                                Settings(viewModel)
//                            }
//                        }
            }
        }
    }
}