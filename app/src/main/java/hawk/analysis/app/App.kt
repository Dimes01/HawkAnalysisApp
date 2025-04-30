package hawk.analysis.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import hawk.analysis.app.nav.DefaultNavigator
import hawk.analysis.app.nav.Destination
import hawk.analysis.app.nav.NavigationAction
import hawk.analysis.app.nav.ObserveAsEvents
import hawk.analysis.app.screens.Home
import hawk.analysis.app.screens.Login
import hawk.analysis.app.screens.Settings
import hawk.analysis.app.ui.theme.HawkAnalysisAppTheme
import hawk.analysis.app.viewmodels.HomeViewModel
import hawk.analysis.app.viewmodels.HomeViewModelFactory
import hawk.analysis.app.viewmodels.LoginViewModel
import hawk.analysis.app.viewmodels.LoginViewModelFactory
import hawk.analysis.app.viewmodels.SettingsViewModel
import hawk.analysis.app.viewmodels.SettingsViewModelFactory

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
                        val viewModel = viewModel<LoginViewModel>(factory = LoginViewModelFactory(navigator))
                        Login(viewModel)
                    }
                }
                navigation<Destination.HomeGraph>(
                    startDestination = Destination.HomeScreen
                ) {
                    composable<Destination.HomeScreen> {
                        val viewModel = viewModel<HomeViewModel>(factory = HomeViewModelFactory(navigator))
                        Home(viewModel)
                    }
                    composable<Destination.SettingsScreen> {
                        val args = it.toRoute<Destination.SettingsScreen>()
                        val viewModel = viewModel<SettingsViewModel>(
                            factory = SettingsViewModelFactory(navigator, args.user, args.account)
                        )
                        Settings(viewModel)
                    }
                }
            }
        }
    }
}