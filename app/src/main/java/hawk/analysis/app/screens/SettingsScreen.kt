package hawk.analysis.app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import hawk.analysis.app.viewmodels.SettingsViewModel

@Composable
fun Settings(viewModel: SettingsViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Пользователь")
        Text("Возраст")
        Button(
            onClick = viewModel::toMain,
        ) {
            Text("На главную")
        }
    }
}

//@Preview
//@Composable
//fun SettingsPreview() {
//    Settings(viewModel = viewModel())
//}