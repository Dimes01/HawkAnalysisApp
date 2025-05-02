package hawk.analysis.app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hawk.analysis.app.viewmodels.SettingsViewModel

@Preview
@Composable
fun SettingsPreview() {
    Settings({})
}

@Composable
fun SettingsVM(viewModel: SettingsViewModel) {
    Settings(
        onMain = viewModel::toMain
    )
}

@Composable
fun Settings(
    onMain: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Пользователь")
        Text("Возраст")
        Button(
            onClick = onMain,
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