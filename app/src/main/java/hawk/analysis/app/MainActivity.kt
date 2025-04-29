package hawk.analysis.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
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
import hawk.analysis.app.viewmodels.LoginViewModel
import hawk.analysis.app.viewmodels.LoginViewModelFactory
import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object AuthGraph : Destination

    @Serializable
    data object HomeGraph : Destination

    @Serializable
    data object LoginScreen : Destination

    @Serializable
    data object RegisterScreen : Destination

    @Serializable
    data object HomeScreen : Destination

    @Serializable
    data class SettingsScreen(val user: User, val account: Account) : Destination
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HawkAnalysisAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    val navigator = DefaultNavigator(Destination.LoginScreen)

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
                        startDestination = navigator.startDestination,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        navigation<Destination.AuthGraph>(
                            startDestination = Destination.LoginScreen
                        ) {
                            composable<Destination.LoginScreen> {
                                val viewModel = viewModel<LoginViewModel>(factory = LoginViewModelFactory(navigator))
                                Login(viewModel)
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
    }
}

