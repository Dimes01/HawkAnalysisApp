package hawk.analysis.app.screens

import android.icu.math.BigDecimal
import android.icu.math.MathContext
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hawk.analysis.app.R
import hawk.analysis.app.dto.AccountAnalyse
import hawk.analysis.app.ui.components.CommonInformation
import hawk.analysis.app.ui.components.HawkHorizontalDivider
import hawk.analysis.app.ui.components.HawkInfoSection
import hawk.analysis.app.ui.components.HawkInfoSectionHeader
import hawk.analysis.app.ui.components.HawkParameter
import hawk.analysis.app.ui.components.HawkParameterRelative
import hawk.analysis.app.ui.theme.HawkAnalysisAppTheme
import hawk.analysis.app.utilities.accountAPI
import hawk.analysis.app.utilities.dateTimeFormat
import hawk.analysis.app.utilities.hawkScale
import hawk.analysis.app.utilities.portfolio
import hawk.analysis.app.utilities.shareNvtk
import hawk.analysis.restlib.contracts.PortfolioResponse
import hawk.analysis.restlib.contracts.Share
import hawk.analysis.restlib.utilities.categoryTranslations
import hawk.analysis.restlib.utilities.toBigDecimal
import kotlinx.datetime.format

@Preview
@Composable
fun AccountPreview() {
    HawkAnalysisAppTheme {
        Account(accountAPI.id, "", { _, _ -> portfolio }, { _, _ -> shareNvtk }, { _ -> emptyList() })
    }
}

@Composable
fun Account(
    accountId: String,
    authToken: String,
    getPortfolio: suspend (authToken: String, accountId: String) -> PortfolioResponse?,
    getShare: suspend (authToken: String, figi: String) -> Share?,
    getAnalyse: suspend (accountId: String) -> List<AccountAnalyse>?,
) {
    val currencyString = stringResource(R.string.currency_rub)
    var portfolio: PortfolioResponse? by remember { mutableStateOf(null) }
    var analyse: AccountAnalyse? by remember { mutableStateOf(null) }
    var sectors by remember { mutableStateOf(HashMap<String, BigDecimal>()) }
    var error by remember { mutableStateOf("") }
    LaunchedEffect(key1 = Unit) {
        portfolio = getPortfolio(authToken, accountId)
        if (portfolio != null) {
            portfolio?.positions?.forEach { pos ->
                getShare(authToken, pos.figi)?.let { share ->
                    val count = pos.quantity.toBigDecimal(0, MathContext.ROUND_FLOOR)
                    val amount = pos.currentPrice.toBigDecimal(2).multiply(count).hawkScale(2)
                    val sector = categoryTranslations[share.sector] ?: "Другое"
                    sectors[sector] = sectors.getOrDefault(sector, BigDecimal.ZERO).add(amount)
                }
            }
            try {
                analyse = getAnalyse(accountId)?.single().also { error = "" }
            } catch (e: Exception) {
                error = "Не удалось получить анализ"
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 15.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        CommonInformation(
            sum = portfolio?.totalAmountPortfolio?.toBigDecimal(2) ?: BigDecimal.ZERO,
            profit = portfolio?.dailyYield?.toBigDecimal(2) ?: BigDecimal.ZERO,
            profitPercent = portfolio?.dailyYieldRelative?.toBigDecimal(2) ?: BigDecimal.ZERO,
            modifier = Modifier.fillMaxWidth()
        )
        val modifierForParams = Modifier.fillMaxWidth().padding(horizontal = 3.dp, vertical = 2.dp)
        val colorParams = MaterialTheme.colorScheme.onTertiaryContainer
        val colorParamsRelative = MaterialTheme.colorScheme.outline
        HawkInfoSection(header = { HawkInfoSectionHeader("Информация") }) {
            HawkParameter("ID", "${portfolio?.accountId}", modifierForParams, colorParams)
            HawkHorizontalDivider()

            val totalAmountShares = portfolio?.totalAmountShares?.toBigDecimal(2)
            if (totalAmountShares?.compareTo(BigDecimal.ZERO) != 0) {
                HawkParameter("Сумма акций", "$totalAmountShares $currencyString", modifierForParams, colorParams)
                HawkHorizontalDivider()
            }

            val totalAmountBonds = portfolio?.totalAmountBonds?.toBigDecimal(2)
            if (totalAmountBonds?.compareTo(BigDecimal.ZERO) != 0) {
                HawkParameter("Сумма облигаций", "$totalAmountBonds $currencyString", modifierForParams, colorParams)
                HawkHorizontalDivider()
            }

            val totalAmountEtf = portfolio?.totalAmountEtf?.toBigDecimal(2)
            if (totalAmountEtf?.compareTo(BigDecimal.ZERO) != 0) {
                HawkParameter("Сумма фондов", "$totalAmountEtf $currencyString", modifierForParams, colorParams)
                HawkHorizontalDivider()
            }

            val totalAmountCurrencies = portfolio?.totalAmountCurrencies?.toBigDecimal(2)
            if (totalAmountCurrencies?.compareTo(BigDecimal.ZERO) != 0) {
                HawkParameter("Сумма валют", "$totalAmountCurrencies $currencyString", modifierForParams, colorParams)
                HawkHorizontalDivider()
            }

            val totalAmountFutures = portfolio?.totalAmountFutures?.toBigDecimal(2)
            if (totalAmountFutures?.compareTo(BigDecimal.ZERO) != 0) {
                HawkParameter("Сумма фьючерсов", "$totalAmountFutures $currencyString", modifierForParams, colorParams)
                HawkHorizontalDivider()
            }

            val totalAmountOptions = portfolio?.totalAmountOptions?.toBigDecimal(2)
            if (totalAmountOptions?.compareTo(BigDecimal.ZERO) != 0) {
                HawkParameter("Сумма опционов", "$totalAmountOptions $currencyString", modifierForParams, colorParams)
                HawkHorizontalDivider()
            }

            val totalAmountSp = portfolio?.totalAmountSp?.toBigDecimal(2)
            if (totalAmountSp?.compareTo(BigDecimal.ZERO) != 0) {
                HawkParameter("Сумма структурных нот", "$totalAmountSp $currencyString", modifierForParams, colorParams)
                HawkHorizontalDivider()
            }

            val totalAmountPortfolio = portfolio?.totalAmountPortfolio?.toBigDecimal(2)
            if (totalAmountPortfolio?.compareTo(BigDecimal.ZERO) != 0) {
                val expectedYield = portfolio?.expectedYield?.toBigDecimal(2)
                val expectedYieldRelative = expectedYield?.divide(totalAmountPortfolio)?.hawkScale()
                HawkParameterRelative(
                    name = "Текущая доходность",
                    value = "$expectedYield $currencyString",
                    valueRelative = "$expectedYieldRelative",
                    modifier = modifierForParams,
                    color = colorParams,
                    colorRelative = colorParamsRelative
                )
                HawkHorizontalDivider()
            }
            val dailyYield = portfolio?.dailyYield?.toBigDecimal(2)
            val dailyYieldRelative = portfolio?.dailyYieldRelative?.toBigDecimal(2)
            if (dailyYield?.compareTo(BigDecimal.ZERO) != 0 && dailyYieldRelative?.compareTo(BigDecimal.ZERO) != 0) {
                HawkParameterRelative(
                    name = "Доходность за день",
                    value = "$dailyYield $currencyString",
                    valueRelative = "$dailyYieldRelative",
                    modifier = modifierForParams,
                    color = colorParams,
                    colorRelative = colorParamsRelative
                )
            }
        }

        if (analyse != null) {
            HawkInfoSection(header = { HawkInfoSectionHeader("Рискованность") }) {
                HawkParameter("Начало анализа", "${analyse?.dateFrom?.format(dateTimeFormat)}", modifierForParams, colorParams)
                HawkHorizontalDivider()
                HawkParameter("Конец анализа", "${analyse?.dateTo?.format(dateTimeFormat)}", modifierForParams, colorParams)
                HawkHorizontalDivider()
                HawkParameter("Средняя доходность", String.format("%.2f%%", analyse!!.mean * 100), modifierForParams, colorParams)
                HawkHorizontalDivider()
                HawkParameter("Стандартное отклонение", String.format("%.2f%%", analyse!!.stdDev * 100), modifierForParams, colorParams)
                if (analyse?.variation != null) {
                    HawkHorizontalDivider()
                    HawkParameter("Коэффициент вариации", String.format("%.2f", analyse!!.variation), modifierForParams, colorParams)
                }
                if (analyse?.sharp != null) {
                    HawkHorizontalDivider()
                    HawkParameter("Коэффициент Шарпа", String.format("%.2f", analyse!!.sharp), modifierForParams, colorParams)
                }
                if (analyse?.information != null) {
                    HawkHorizontalDivider()
                    HawkParameter("Коэффициент информации", String.format("%.2f", analyse!!.information), modifierForParams, colorParams)
                }
                if (analyse?.sortino != null) {
                    HawkHorizontalDivider()
                    HawkParameter("Коэффициент Сортино", String.format("%.2f", analyse!!.sortino), modifierForParams, colorParams)
                }
            }
        }
//        HawkInfoSection(
//            header = { HawkInfoSectionHeader("Отрасли") }
//        ) {
//            sectors.forEach { sector ->
//                portfolio?.totalAmountPortfolio?.toBigDecimal(2)?.let { total ->
//                    val part = if (total.compareTo(BigDecimal.ZERO) != 0) sector.value.divide(total).hawkScale(2)
//                        else BigDecimal.ZERO
//                    HawkParameterRelative(
//                        name = sector.key,
//                        value = "${sector.value}",
//                        valueRelative = "$part",
//                        modifier = modifierForParams,
//                        color = colorParams,
//                        colorRelative = colorParamsRelative
//                    )
//                }
//            }
//        }
    }
}