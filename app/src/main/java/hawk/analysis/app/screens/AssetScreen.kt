package hawk.analysis.app.screens

import android.icu.math.MathContext
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hawk.analysis.app.dto.AssetAnalyse
import hawk.analysis.app.ui.components.HawkHorizontalDivider
import hawk.analysis.app.ui.components.HawkInfoSection
import hawk.analysis.app.ui.components.HawkInfoSectionHeader
import hawk.analysis.app.ui.components.HawkParameter
import hawk.analysis.app.ui.components.HawkSimpleHeader
import hawk.analysis.app.ui.theme.HawkAnalysisAppTheme
import hawk.analysis.app.utilities.HawkResponse
import hawk.analysis.app.utilities.accountAPI
import hawk.analysis.app.utilities.assetAnalyse
import hawk.analysis.app.utilities.dateTimeFormat
import hawk.analysis.app.utilities.portfolio
import hawk.analysis.app.utilities.shareNvtk
import hawk.analysis.restlib.contracts.PortfolioPosition
import hawk.analysis.restlib.contracts.PortfolioResponse
import hawk.analysis.restlib.contracts.Share
import hawk.analysis.restlib.utilities.toBigDecimal
import kotlinx.datetime.format

@Preview(widthDp = 440, heightDp = 956, showBackground = true)
@Composable
fun AssetPreview() {
    HawkAnalysisAppTheme {
        Asset(
            uid = shareNvtk.figi,
            authToken = "",
            accountId = accountAPI.id,
            getInfo = { _, _ -> shareNvtk },
            getPortfolioAsset = { _, _ -> portfolio },
            getAnalysis = { _ -> listOf(assetAnalyse) },
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer).padding(10.dp)
        )
    }
}

@Composable
fun Asset(
    uid: String,
    authToken: String,
    accountId: String,
    getInfo: suspend (authToken: String, figi: String) -> HawkResponse<Share>,
    getPortfolioAsset: suspend (authToken: String, accountId: String) -> HawkResponse<PortfolioResponse>,
    getAnalysis: suspend (accountId: String) -> HawkResponse<List<AssetAnalyse>>,
    modifier: Modifier = Modifier
) {
    var info: Share? by remember { mutableStateOf(null) }
    var portAsset: PortfolioPosition? by remember { mutableStateOf(null) }
    var assetAnalyse: AssetAnalyse? by remember { mutableStateOf(null) }
    LaunchedEffect(key1 = Unit) {
        info = getInfo(authToken, uid)
        portAsset = getPortfolioAsset(authToken, accountId)?.positions?.firstOrNull { it.figi == uid }
        if (portAsset != null)
            assetAnalyse = getAnalysis(accountId)?.firstOrNull { it.securitiesId == portAsset?.instrumentUid }
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HawkSimpleHeader(
            name = info?.name ?: "",
            modifier = Modifier.padding(10.dp),
            label = {
                Row {
                    Text(
                        text = info?.ticker ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.outline,
                    )
//                    HawkVerticalDivider()
                    Text(
                        text = info?.figi ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.outline,
                    )
                }
            }
        )
        val modifierForParams = Modifier.fillMaxWidth().padding(horizontal = 3.dp, vertical = 2.dp)
        val colorParams = MaterialTheme.colorScheme.onTertiaryContainer
        HawkInfoSection(header = { HawkInfoSectionHeader("Информация") }) {
            HawkParameter("Название", info?.name ?: "", modifierForParams, colorParams)
            HawkHorizontalDivider()
            HawkParameter("Страна", info?.countryOfRiskName ?: "", modifierForParams, colorParams)
            HawkHorizontalDivider()
            HawkParameter("Биржа", info?.exchange ?: "", modifierForParams, colorParams)
            HawkHorizontalDivider()
            HawkParameter("Отрасль", info?.sector ?: "", modifierForParams, colorParams)
        }
        HawkInfoSection(header = { HawkInfoSectionHeader("Стоимость") }) {
            HawkParameter("Лотность", "${info?.lot}", modifierForParams, colorParams)
            HawkHorizontalDivider()
            val quantity = portAsset?.quantity?.toBigDecimal()?.setScale(0, MathContext.ROUND_FLOOR)
            HawkParameter("Количество в портфеле", "$quantity", modifierForParams, colorParams)
            HawkHorizontalDivider()
            val meanFifo = portAsset?.averagePositionPriceFifo?.toBigDecimal()?.setScale(2, MathContext.ROUND_HALF_UP)
            HawkParameter("Средняя цена по FIFO", "$meanFifo", modifierForParams, colorParams)
            HawkHorizontalDivider()
            val expectedYield = portAsset?.expectedYield?.toBigDecimal()?.setScale(2, MathContext.ROUND_HALF_UP)
            HawkParameter("Текущая доходность", "$expectedYield", modifierForParams, colorParams)
            HawkHorizontalDivider()
            val dailyYield = portAsset?.dailyYield?.toBigDecimal()?.setScale(2, MathContext.ROUND_HALF_UP)
            HawkParameter("Доходность за день", "$dailyYield", modifierForParams, colorParams)
        }
        HawkInfoSection(header = { HawkInfoSectionHeader("Рискованность") }) {
            HawkParameter("Начало анализа", "${assetAnalyse?.dateFrom?.format(dateTimeFormat)}", modifierForParams, colorParams)
            HawkHorizontalDivider()
            HawkParameter("Конец анализа", "${assetAnalyse?.dateTo?.format(dateTimeFormat)}", modifierForParams, colorParams)
            HawkHorizontalDivider()
            HawkParameter("Средняя цена", "${assetAnalyse?.mean}", modifierForParams, colorParams)
            HawkHorizontalDivider()
            HawkParameter("Стандартное отклонение", "${assetAnalyse?.stdDev}", modifierForParams, colorParams)
            HawkHorizontalDivider()
            HawkParameter("Коэффициент вариации", "${assetAnalyse?.variation}", modifierForParams, colorParams)
            HawkHorizontalDivider()
            HawkParameter("Коэффициент Шарпа", "${assetAnalyse?.sharp}", modifierForParams, colorParams)
            HawkHorizontalDivider()
            HawkParameter("Коэффициент информации", "${assetAnalyse?.information}", modifierForParams, colorParams)
            HawkHorizontalDivider()
            HawkParameter("Коэффициент Сортино", "${assetAnalyse?.sortino}", modifierForParams, colorParams)
        }
    }
}

