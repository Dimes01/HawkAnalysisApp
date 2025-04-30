package hawk.analysis.app.viewmodels

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import hawk.analysis.app.Destination
import hawk.analysis.app.HomeScreen
import hawk.analysis.app.nav.Navigator
import hawk.analysis.app.utilities.Provider
import kotlinx.coroutines.launch

class LoginViewModel(
    private val navigator: Navigator
) : ViewModel() {
//    companion object {
//        val NAVIGATOR_KEY = object : CreationExtras.Key<Navigator> {}
//
//        val Factory: ViewModelProvider.Factory = viewModelFactory {
//            initializer {
//                val navigator = this[NAVIGATOR_KEY] as Navigator
//                LoginViewModel(navigator)
//            }
//        }
//    }


    var name = mutableStateOf("")
    var age = mutableIntStateOf(18)

    fun toMainScreen() {
        Provider.name = name.value
        Provider.age = age.intValue
        viewModelScope.launch {
            navigator.navigate(destination = HomeScreen)
        }
    }
}

class LoginViewModelFactory(private val navigator: Navigator) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(navigator) as T
    }
}