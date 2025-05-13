package hawk.analysis.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hawk.analysis.app.R
import hawk.analysis.app.ui.components.CommonInformation
import hawk.analysis.app.ui.components.Header
import hawk.analysis.app.ui.theme.HawkAnalysisAppTheme
import hawk.analysis.app.utilities.accountTI
import hawk.analysis.app.utilities.cuurencies
import hawk.analysis.app.utilities.state
import hawk.analysis.app.utilities.timeFormat
import hawk.analysis.app.viewmodels.HomeViewModel
import hawk.analysis.restlib.contracts.Account
import kotlinx.datetime.format

@Composable
fun HomeVM(viewModel: HomeViewModel = viewModel()) {
    val selectedAccount = viewModel.currentAccount.collectAsState()
    val state = viewModel.currentState.collectAsState()
    Home(
        selectedAccount = selectedAccount.value,
        state = state.value,
        modifier = Modifier.fillMaxSize(),
        onUpdateAccount = viewModel::updateAccounts,
        onPrevAccount = viewModel::selectPreviousAccount,
        onNextAccount = viewModel::selectNextAccount
    )
}

@Composable
fun Home(
    selectedAccount: Account?,
    state: HomeScreenState,
    modifier: Modifier = Modifier,
    onUpdateAccount: () -> Unit,
    onPrevAccount: () -> Unit,
    onNextAccount: () -> Unit,
) {
    val defaultCurrency = R.string.currency_rub
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var modifierMainPart: Modifier = Modifier
        selectedAccount?.let { account ->
            Header(
                account = account,
                lastUpdatedAt = state.lastUpdatedAt,
                modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerHighest),
                onPrevClick = onPrevAccount,
                onNextClick = onNextAccount,
            )
            modifierMainPart = Modifier.fillMaxSize().padding(horizontal = 15.dp, vertical = 10.dp)
        } ?: Column {
            Text(
                text = "No account selected",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Последнее обновление: ${state.lastUpdatedAt.format(timeFormat)}",
                style = MaterialTheme.typography.bodyLarge
            )
            Button(onClick = onUpdateAccount) {
                Text("Обновить счета")
            }
        }
        Column(
            modifier = modifierMainPart,
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val modifierCommonInfo = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
            CommonInformation(sum = state.sum, profit = state.profit, profitPercent = state.profitRelative, modifier = modifierCommonInfo)
            Column(
                modifier = Modifier.padding(vertical = 5.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // TODO: использовать что-то наподобие MoneyState, включающего Currency и информацию из Portfolio
                Section("Деньги") {
                    items(state.money) { money ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Text(
                                        text = money.name,
                                        style = MaterialTheme.typography.headlineMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = money.name,
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                }
                                Text(
                                    text = "",
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                            }
                            VerticalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                            Text(
                                text = stringResource(cuurencies.getOrDefault(money.currency, defaultCurrency)),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Section(name: String, content: LazyListScope.() -> Unit) {
    Text(
        text = name,
        style = MaterialTheme.typography.displaySmall,
        color = MaterialTheme.colorScheme.onSurface
    )
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        content = content
    )
}

@Preview(widthDp = 440, heightDp = 956)
@Composable
fun HomePreview() {
    HawkAnalysisAppTheme {
        Home(
            selectedAccount = accountTI,
            state = state,
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceContainer),
            onUpdateAccount = {},
            onPrevAccount = {},
            onNextAccount = {}
        )
    }
}