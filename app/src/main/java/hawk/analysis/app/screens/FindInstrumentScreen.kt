package hawk.analysis.app.screens

import android.content.ClipData
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hawk.analysis.app.ui.components.ErrorMessage
import hawk.analysis.app.ui.components.HawkInfoSectionHeader
import hawk.analysis.app.ui.components.HawkOutlinedButton
import hawk.analysis.app.ui.components.HawkOutlinedTextField
import hawk.analysis.app.ui.components.HawkSimpleHeader
import hawk.analysis.app.ui.components.HawkTonalButton
import hawk.analysis.app.ui.theme.HawkAnalysisAppTheme
import hawk.analysis.app.utilities.findInstruments
import hawk.analysis.restlib.contracts.FindInstrumentResponse
import hawk.analysis.restlib.contracts.InstrumentShort
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview
@Composable
fun FindInstrumentPreview() {
    HawkAnalysisAppTheme {
        FindInstrument("", { _, _ -> findInstruments }, {})
    }
}

@Composable
fun FindInstrument(
    authToken: String,
    actFind: suspend (String, String) -> FindInstrumentResponse?,
    navToBack: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val instruments = remember { mutableStateListOf<InstrumentShort>() }
    var query: String by remember { mutableStateOf("") }
    var errorFind: String? by remember { mutableStateOf(null) }
    LaunchedEffect(key1 = Unit) {
        instruments.addAll(actFind(authToken, query)?.instruments ?: emptyList<InstrumentShort>())
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainer),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HawkSimpleHeader("Поиск инструмента", Modifier.padding(vertical = 20.dp))
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HawkOutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = "Название или идентификатор",
                modifier = Modifier.fillMaxWidth()
            )
            HawkTonalButton(
                text = "Поиск",
                modifier = Modifier.fillMaxWidth(),
                onClick = { coroutineScope.launch {
                    actFind(authToken, query).let {
                        if (it == null) errorFind = "Произошла ошибка при поиске"
                        else {
                            errorFind = null
                            instruments.clear()
                            instruments.addAll(it.instruments)
                        }
                    }
                } }
            )
            if (errorFind != null) { ErrorMessage(errorFind!!) }
            HorizontalDivider()
            HawkInfoSectionHeader("Результаты поиска")
            val clipboard = LocalClipboard.current
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(instruments) { instrument -> FindInstrumentCard(instrument, clipboard, navToBack) }
            }
            HawkOutlinedButton(
                text = "Отменить",
                borderColor = MaterialTheme.colorScheme.onErrorContainer,
                contentColor = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth(),
                onClick = navToBack
            )
        }
    }
}

@Composable
fun FindInstrumentCard(
    info: InstrumentShort, clipboard: Clipboard, navToBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    OutlinedCard(
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = info.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = info.ticker,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = info.figi,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textDecoration = TextDecoration.Underline,
                    softWrap = false,
                    modifier = Modifier.clickable(onClick = {
                        coroutineScope.launch {
                            clipboard.setClipEntry(ClipEntry(ClipData.newPlainText("FIGI", info.figi)))
                            snackbarHostState.showSnackbar("FIGI скопирован")
                            delay(100)
                        }
                        navToBack()
                    } )
                )
            }
        }
    }
}