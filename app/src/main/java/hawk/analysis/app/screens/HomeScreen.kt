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
import androidx.compose.ui.tooling.preview.Preview
import hawk.analysis.app.viewmodels.HomeViewModel

@Composable
fun Home(
    onSettings: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onSettings,
        ) {
            Text("Настройки")
        }
    }
}

@Composable
fun HomeVM(viewModel: HomeViewModel) {
    Home(
        onSettings = viewModel::toSettings
    )
}

@Preview
@Composable
fun HomePreview() {
    Home({})
}
