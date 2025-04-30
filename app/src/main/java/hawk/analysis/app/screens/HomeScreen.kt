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
import androidx.lifecycle.viewmodel.compose.viewModel
import hawk.analysis.app.viewmodels.HomeViewModel

@Composable
fun Home() {
    val viewModel: HomeViewModel = viewModel()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = viewModel::toSettings,
        ) {
            Text("Настройки")
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    Home()
}
