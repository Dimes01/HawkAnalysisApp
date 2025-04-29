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
import androidx.lifecycle.viewmodel.compose.viewModel
import hawk.analysis.app.viewmodels.LoginViewModel

@Composable
fun Login(viewModel: LoginViewModel = viewModel()) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var name = ""
        var age = 18
        Text("Имя")
        TextField(
            value = name,
            onValueChange = { name = it }
        )
        Text("Возраст")
        TextField(
            value = age.toString(),
            onValueChange = { age = it.toInt() }
        )
        Button(
            onClick = {
                viewModel.name.value = name
                viewModel.age.intValue = age
                viewModel.toMainScreen()
            },
        ) {
            Text("На главную")
        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    Login()
}