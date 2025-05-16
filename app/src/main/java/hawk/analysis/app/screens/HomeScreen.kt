package hawk.analysis.app.screens

import android.icu.math.BigDecimal
import android.icu.math.MathContext
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.QueryStats
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hawk.analysis.app.R
import hawk.analysis.app.ui.components.CommonInformation
import hawk.analysis.app.ui.components.Header
import hawk.analysis.app.ui.theme.HawkAnalysisAppTheme
import hawk.analysis.app.ui.theme.negativeColor
import hawk.analysis.app.ui.theme.positiveColor
import hawk.analysis.app.utilities.accountTI
import hawk.analysis.app.utilities.cuurencies
import hawk.analysis.app.utilities.state
import hawk.analysis.app.utilities.timeFormat
import hawk.analysis.app.viewmodels.HomeViewModel
import hawk.analysis.restlib.contracts.Account
import hawk.analysis.restlib.utilities.toBigDecimal
import kotlinx.datetime.format

@Composable
fun HomeVM(viewModel: HomeViewModel) {
    val selectedAccount = viewModel.currentAccount.collectAsState()
    val state = viewModel.currentState.collectAsState()
    Home(
        selectedAccount = selectedAccount.value,
        state = state.value,
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
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
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
            modifierMainPart = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)
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
            val shapeBorder = RoundedCornerShape(10.dp)
            CommonInformation(sum = state.sum, profit = state.profit, profitPercent = state.profitRelative, modifier = modifierCommonInfo)
            Column(
                modifier = Modifier.padding(vertical = 5.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                HawkSection("Деньги") {
                    items(state.money) { money ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(3.dp, MaterialTheme.colorScheme.outline, shapeBorder)
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically)
                            ) {
                                Text(
                                    text = money.general.name,
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    softWrap = false
                                )
                                val countryName = money.general.countryOfRiskName
                                Text(
                                    text = if (countryName.isNotEmpty()) countryName else "Страна не определена",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.secondary,
                                    softWrap = false
                                )
                            }
                            HawkPrice("${money.value.toBigDecimal().setScale(2, MathContext.ROUND_HALF_UP)}")
                            VerticalDivider(
                                color = MaterialTheme.colorScheme.outlineVariant,
                                thickness = 1.dp,
                            )
                            Text(
                                text = stringResource(cuurencies.getOrDefault(money.value.currency, defaultCurrency)),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                softWrap = false
                            )
                        }
                    }
                }
                HawkSection("Акции") {
                    items(state.shares) { share ->
                        val colorBorder = when (share.sum.compareTo(BigDecimal.ZERO)) {
                            1 -> positiveColor
                            -1 -> negativeColor
                            else -> MaterialTheme.colorScheme.outline
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(3.dp, colorBorder, shapeBorder)
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Text(
                                    text = share.name,
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    softWrap = false
                                )
                                HawkCount("Кол-во", share.count.toString())
                                HawkCount("Лотов", share.countOfLots.toString())
                            }
                            Column(
                                verticalArrangement = Arrangement.spacedBy(2.dp),
                                horizontalAlignment = Alignment.End
                            ) {
                                HawkPrice(share.sum.setScale(2).toString())
                                HawkSmallPrice("${share.profitPercent.setScale(2, MathContext.ROUND_HALF_UP)} %", colorBorder)
                                HawkSmallPrice("${share.profit.setScale(2, MathContext.ROUND_HALF_UP)}", colorBorder)
                            }
                            VerticalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                            Column {
                                Text(
                                    text = stringResource(cuurencies.getOrDefault(share.currencyCode, defaultCurrency)),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Icon(
                                    imageVector = Icons.Outlined.QueryStats,
                                    contentDescription = "Stats"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HawkCount(label: String, count: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            softWrap = false
        )
        Text(
            text = "$count:",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            softWrap = false
        )
    }
}

@Composable
fun HawkPrice(price: String) {
    Text(
        text = price,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.tertiary,
        softWrap = false
    )
}

@Composable
fun HawkSmallPrice(price: String, color: Color) {
    Text(
        text = price,
        style = MaterialTheme.typography.bodyMedium,
        color = color
    )
}

@Composable
fun HawkSection(name: String, content: LazyListScope.() -> Unit) {
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

@Preview()
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