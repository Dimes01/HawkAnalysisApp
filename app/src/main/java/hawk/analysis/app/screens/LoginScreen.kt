package hawk.analysis.app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hawk.analysis.app.viewmodels.LoginViewModel

@Composable
fun LoginVM(viewModel: LoginViewModel) {
    Login(
        onNameChanged = { viewModel.name.value = it },
        onPasswordChanged = { viewModel.password.value = it },
        onHomeScreen = viewModel::toMainScreen
    )
}

@Preview
@Composable
fun LoginPreview() {
    Login({}, {}, {})
}

@Composable
fun Login(
    onNameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onHomeScreen: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var name = ""
        var password = ""
        Text("Имя")
        TextField(
            value = name,
            onValueChange = { name = it }
        )
        Text("Пароль")
        TextField(
            value = password,
            onValueChange = { password = it }
        )
        Button(
            onClick = {
                onNameChanged(name)
                onPasswordChanged(password)
                onHomeScreen()
            },
        ) {
            Text("На главную")
        }
    }
}