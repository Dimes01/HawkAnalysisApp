package hawk.analysis.app.screens

import android.icu.math.BigDecimal
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hawk.analysis.app.dto.AccountInfo
import hawk.analysis.app.ui.components.ErrorMessage
import hawk.analysis.app.ui.components.HawkInfoSection
import hawk.analysis.app.ui.components.HawkInfoSectionHeader
import hawk.analysis.app.ui.components.HawkOutlinedButton
import hawk.analysis.app.ui.components.HawkOutlinedTextField
import hawk.analysis.app.ui.components.HawkParameter
import hawk.analysis.app.ui.components.HawkSimpleHeader
import hawk.analysis.app.ui.theme.HawkAnalysisAppTheme
import kotlinx.coroutines.launch

@Preview(widthDp = 440, heightDp = 956)
@Composable
fun EditAccountPreview() {
    HawkAnalysisAppTheme {
        EditAccount("1234567890", { _, _ -> null }, { _, _ -> null }, {})
    }
}

@Composable
fun EditAccount(
    accountId: String,
    actChangeRiskFree: suspend (String, BigDecimal?) -> AccountInfo?,
    actChangeBenchmark: suspend (String, String?) -> AccountInfo?,
    navToBack: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    var riskFree by remember { mutableStateOf("") }
    var tickerBenchmark by remember { mutableStateOf("") }
    var errorRiskFree: String? by remember { mutableStateOf(null) }
    var errorBenchmark: String? by remember { mutableStateOf(null) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HawkSimpleHeader("Изменение счета")
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HawkParameter("ID", accountId)
            HawkInfoSection(
                header = { HawkInfoSectionHeader("Безрисковая ставка") }
            ) {
                HawkOutlinedTextField(
                    value = riskFree,
                    onValueChange = { riskFree = it },
                    label = "Безрисковая ставка",
                    modifier = Modifier.fillMaxWidth()
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    HawkOutlinedButton("Сохранить") { coroutineScope.launch {
                        val info = actChangeRiskFree(accountId, if (riskFree.isNotEmpty()) BigDecimal(riskFree) else null )
                        if (info == null) errorRiskFree = "Не удалось изменить ставку"
                    } }
                }
            }
            errorRiskFree?.also { ErrorMessage(it) }
            HawkInfoSection(
                header = { HawkInfoSectionHeader("Бенчмарк") }
            ) {
                HawkOutlinedTextField(
                    value = tickerBenchmark,
                    onValueChange = { tickerBenchmark = it },
                    label = "Тикер бенчмарка",
                    modifier = Modifier.fillMaxWidth()
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    HawkOutlinedButton("Сохранить") { coroutineScope.launch {
                        val info = actChangeBenchmark(accountId, if (tickerBenchmark.isNotEmpty()) tickerBenchmark else null)
                        if (info == null) errorBenchmark = "Не удалось изменить бенчмарк"
                    } }
                }
            }
            errorBenchmark?.also { ErrorMessage(it) }
            HawkOutlinedButton(
                text = "Вернуться",
                borderColor = MaterialTheme.colorScheme.onErrorContainer,
                contentColor = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth(),
                onClick = navToBack
            )
        }
    }
}