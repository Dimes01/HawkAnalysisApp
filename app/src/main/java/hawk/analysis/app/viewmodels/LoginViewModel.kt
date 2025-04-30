package hawk.analysis.app.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hawk.analysis.app.nav.Destination
import hawk.analysis.app.nav.Navigator
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    var name = mutableStateOf("")
        private set
    var password = mutableStateOf("")
        private set
}
