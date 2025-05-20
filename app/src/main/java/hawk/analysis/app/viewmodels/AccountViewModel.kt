package hawk.analysis.app.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController

class AccountViewModel(
    private val navController: NavController,
) : ViewModel() {
    companion object {
        val NAV_CONTROLLER = object : CreationExtras.Key<NavController> {}
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val navController = this[NAV_CONTROLLER] as NavController
                AccountViewModel(navController)
            }
        }
    }


}