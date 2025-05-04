package hawk.analysis.app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowLeft
import androidx.compose.material.icons.automirrored.outlined.ArrowRight
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hawk.analysis.app.viewmodels.HomeViewModel

@Preview
@Composable
fun HomePreview() {
    Home(null, {}, {})
}

@Composable
fun HomeVM(viewModel: HomeViewModel = viewModel()) {
//    val selectedAccount = viewModel.selectedAccount.collectAsState()

    Home(
        selectedAccount = viewModel.selectedAccount,
        onPrevAccount = viewModel::previousAccount,
        onNextAccount = viewModel::nextAccount
    )
}

@Composable
fun Home(
    selectedAccount: Any?,
    onPrevAccount: () -> Unit,
    onNextAccount: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        selectedAccount?.let { account ->
            Header(
                account = account,
                modifier = Modifier.padding(vertical = 10.dp),
                onPrevClick = onPrevAccount,
                onNextClick = onNextAccount,
            )
        } ?: Text("No account selected", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun Header(
    account: Any,
    modifier: Modifier = Modifier,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onPrevClick) { Icons.AutoMirrored.Outlined.ArrowLeft }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "account.name",
                style = MaterialTheme.typography.displaySmall
            )
            Text(
                text = "account.id",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        IconButton(onClick = onNextClick) { Icons.AutoMirrored.Outlined.ArrowRight }
    }
}