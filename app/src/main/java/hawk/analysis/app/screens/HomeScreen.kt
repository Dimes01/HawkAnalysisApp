package hawk.analysis.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import hawk.analysis.app.ui.components.Header
import hawk.analysis.app.ui.theme.HawkAnalysisAppTheme
import hawk.analysis.app.utilities.timeFormat
import hawk.analysis.app.viewmodels.HomeViewModel
import hawk.analysis.restlib.models.Account
import hawk.analysis.restlib.enums.AccessLevel
import hawk.analysis.restlib.enums.AccountStatus
import hawk.analysis.restlib.enums.AccountType
import kotlinx.datetime.Instant
import kotlinx.datetime.format

@Composable
fun HomeVM(viewModel: HomeViewModel = viewModel()) {
    val selectedAccount = viewModel.selectedAccount.collectAsState()

    Home(
        selectedAccount = selectedAccount.value,
        lastUpdatedAt = viewModel.lastUpdatedAt.value,
        modifier = Modifier.fillMaxSize(),
        onUpdateAccount = viewModel::updateAccounts,
        onPrevAccount = viewModel::previousAccount,
        onNextAccount = viewModel::nextAccount
    )
}

@Composable
fun Home(
    selectedAccount: Account?,
    lastUpdatedAt: Instant,
    modifier: Modifier = Modifier,
    onUpdateAccount: () -> Unit,
    onPrevAccount: () -> Unit,
    onNextAccount: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var modifierMainPart: Modifier = Modifier
        selectedAccount?.let { account ->
            Header(
                account = account,
                lastUpdatedAt = lastUpdatedAt,
                modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerHighest),
                onPrevClick = onPrevAccount,
                onNextClick = onNextAccount,
            )
            modifierMainPart = Modifier.fillMaxSize()
        } ?: Column {
            Text(
                text = "No account selected",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Последнее обновление: ${lastUpdatedAt.format(timeFormat)}",
                style = MaterialTheme.typography.bodyLarge
            )
            Button(onClick = onUpdateAccount) {
                Text("Обновить счета")
            }
        }
        Column(
            modifier = modifierMainPart
        ) {

        }
    }
}

@Preview(widthDp = 440, heightDp = 956)
@Composable
fun HomePreview() {
    HawkAnalysisAppTheme {
        val account = Account(
            id = "2156732337",
            type = AccountType.ACCOUNT_TYPE_INVEST_BOX,
            name = "Брокерский счет",
            status = AccountStatus.ACCOUNT_STATUS_OPEN,
            openedDate = Instant.parse("2022-03-02T00:00:00Z"),
            closedDate = Instant.parse("1970-01-01T00:00:00Z"),
            accessLevel = AccessLevel.ACCOUNT_ACCESS_LEVEL_READ_ONLY
        )
        Home(
            selectedAccount = account,
            lastUpdatedAt = Instant.parse("2022-03-02T09:12:34Z"),
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceContainer),
            onUpdateAccount = {},
            onPrevAccount = {},
            onNextAccount = {}
        )
    }
}