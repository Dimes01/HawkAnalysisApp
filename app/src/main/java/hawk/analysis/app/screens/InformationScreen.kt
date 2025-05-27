package hawk.analysis.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import hawk.analysis.app.ui.components.HawkInfoSection
import hawk.analysis.app.ui.components.HawkInfoSectionHeader
import hawk.analysis.app.ui.components.HawkSimpleHeader
import hawk.analysis.app.ui.theme.HawkAnalysisAppTheme

@Preview(widthDp = 440, heightDp = 2000)
@Composable
fun InformationPreview() {
    HawkAnalysisAppTheme {
        Information()
    }
}

@Composable
fun Information() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .verticalScroll(rememberScrollState())
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HawkSimpleHeader("Информация", Modifier.padding(vertical = 20.dp))
        // Средняя доходность
        HawkInfoSection(header = { HawkInfoSectionHeader("Средняя доходность") }) {
            HawkPlainText("Среднее значение всех доходностей за период.")
            HawkBoldText("Формула:")
            HawkFormulaText("Avg = Σ(Ri) / n")
            HawkPlainText("Где:")
            HawkBulletedText("Ri - отдельная доходность")
            HawkBulletedText("n - количество периодов")
            HawkDivider()
            HawkBoldText("Пример:")
            Row(verticalAlignment = Alignment.CenterVertically) {
                HawkPlainText("Доходности: ")
                HawkValueText("1%, 2%, -4%, 5%")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                HawkPlainText("Средняя: ")
                HawkValueText("(1 + 2 - 4 + 5) / 4 = 1%")
            }
        }

        // Стандартное отклонение
        HawkInfoSection(header = { HawkInfoSectionHeader("Стандартное отклонение") }) {
            HawkPlainText("Мера разброса данных относительно среднего.")
            HawkBoldText("Формула:")
            HawkFormulaText("σ = √[Σ(Ri - Avg)² / n]")
            HawkDivider()
            HawkBoldText("Интерпретация:")
            HawkBulletedText("Низкое: <5% - стабильные результаты")
            HawkBulletedText("Среднее: 5-15% - умеренный риск")
            HawkBulletedText("Высокое: >15% - высокая волатильность")
            HawkDivider()
            HawkBoldText("Пример:")
            HawkPlainText("При среднем 10% и отклонении 5%:")
            Row(verticalAlignment = Alignment.CenterVertically) {
                HawkPlainText("68% значений в диапазоне ")
                HawkValueText("5-15%")
            }
        }

        // Коэффициент вариации
        HawkInfoSection(header = { HawkInfoSectionHeader("Коэффициент вариации") }) {
            HawkPlainText("Относительная мера риска на единицу доходности.")
            HawkBoldText("Формула:")
            HawkFormulaText("CV = σ / Avg")
            HawkDivider()
            HawkBoldText("Интерпретация:")
            HawkBulletedText("<0.5 - отличное соотношение")
            HawkBulletedText("0.5-1 - приемлемый риск")
            HawkBulletedText(">1 - высокий относительный риск")
            HawkDivider()
            HawkBoldText("Пример:")
            Row(verticalAlignment = Alignment.CenterVertically) {
                HawkPlainText("При σ=3% и Avg=6%: ")
                HawkValueText("CV = 3/6 = 0.5")
            }
        }

        // Коэффициент Шарпа
        HawkInfoSection(header = { HawkInfoSectionHeader("Коэффициент Шарпа") }) {
            HawkPlainText("Доходность на единицу общего риска.")
            HawkBoldText("Формула:")
            HawkFormulaText("Sharpe = (Rp - Rf) / σ")
            HawkPlainText("Где:")
            HawkBulletedText("Rp - доходность портфеля")
            HawkBulletedText("Rf - безрисковая ставка")
            HawkBulletedText("σ - стандартное отклонение")
            HawkDivider()
            HawkBoldText("Интерпретация:")
            HawkBulletedText("<1 - низкая эффективность")
            HawkBulletedText("1-2 - хороший показатель")
            HawkBulletedText(">2 - отличный результат")
            HawkDivider()
            HawkBoldText("Пример:")
            Row(verticalAlignment = Alignment.CenterVertically) {
                HawkPlainText("Rp=10%, Rf=3%, σ=4%: ")
                HawkValueText("(10-3)/4 = 1.75")
            }
        }

        // Коэффициент информации
        HawkInfoSection(header = { HawkInfoSectionHeader("Коэффициент информации") }) {
            HawkPlainText("Стабильность превосходства над бенчмарком.")
            HawkBoldText("Формула:")
            HawkFormulaText("IR = (Rp - Rb) / TE")
            HawkPlainText("Где:")
            HawkBulletedText("Rp - доходность портфеля")
            HawkBulletedText("Rb - доходность бенчмарка")
            HawkBulletedText("TE - tracking error")
            HawkDivider()
            HawkBoldText("Интерпретация:")
            HawkBulletedText("<0.4 - слабое превосходство")
            HawkBulletedText("0.4-0.7 - хороший результат")
            HawkBulletedText(">0.7 - отличный менеджмент")
            HawkDivider()
            HawkBoldText("Пример:")
            Row(verticalAlignment = Alignment.CenterVertically) {
                HawkPlainText("Rp=12%, Rb=10%, TE=3%: ")
                HawkValueText("(12-10)/3 ≈ 0.67")
            }
        }

        // Коэффициент Сортино
        HawkInfoSection(header = { HawkInfoSectionHeader("Коэффициент Сортино") }) {
            HawkPlainText("Доходность с учетом только downside риска.")
            HawkBoldText("Формула:")
            HawkFormulaText("Sortino = (Rp - Rf) / σd")
            HawkPlainText("Где:")
            HawkBulletedText("σd - downside deviation")
            HawkDivider()
            HawkBoldText("Интерпретация:")
            HawkBulletedText("<1 - высокий риск просадок")
            HawkBulletedText("1-2 - приемлемый уровень")
            HawkBulletedText(">2 - отличная защита")
            HawkDivider()
            HawkBoldText("Пример:")
            Row(verticalAlignment = Alignment.CenterVertically) {
                HawkPlainText("Rp=8%, Rf=3%, σd=2%: ")
                HawkValueText("(8-3)/2 = 2.5")
            }
        }

        // Сравнительная таблица
        HawkInfoSection(header = { HawkInfoSectionHeader("Сравнение метрик") }) {
            val weightMetrics = 1.5f
            val weightWhatAssume = 2f
            HawkPlainText("Основные отличия ключевых коэффициентов:")

            // Строка 1 - Заголовки
            Row {
                HawkBoldText("Метрика", modifier = Modifier.weight(weightMetrics))
                HawkBoldText("Что учитывает", modifier = Modifier.weight(weightWhatAssume))
            }

            // Разделитель
            HawkDivider()

            // Строки данных
            Row {
                HawkPlainText("Sharpe", modifier = Modifier.weight(weightMetrics))
                HawkPlainText("Общий риск (σ)", modifier = Modifier.weight(weightWhatAssume))
            }

            Row {
                HawkPlainText("Sortino", modifier = Modifier.weight(weightMetrics))
                HawkPlainText("Downside risk (σd)", modifier = Modifier.weight(weightWhatAssume))
            }

            Row {
                HawkPlainText("IR", modifier = Modifier.weight(weightMetrics))
                HawkPlainText("Отклонение от бенчмарка", modifier = Modifier.weight(weightWhatAssume))
            }
        }
    }
}

@Composable
fun HawkPlainText(
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = MaterialTheme.colorScheme.outline,
    modifier: Modifier = Modifier
) {
    Text(text = text, style = style, color = color, modifier = modifier)
}

@Composable
fun HawkBoldText(
    text: String,
    style: TextStyle = MaterialTheme.typography.titleMedium,
    color: Color = MaterialTheme.colorScheme.outline,
    fontWeight: FontWeight = FontWeight.Bold,
    modifier: Modifier = Modifier
) {
    Text(text = text, style = style, color = color, fontWeight = fontWeight, modifier = modifier)
}

// Для маркированных списков
@Composable
fun HawkBulletedText(
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = MaterialTheme.colorScheme.outline,
    bullet: String = "•"
) {
    Row(verticalAlignment = Alignment.Top) {
        Text(text = "$bullet ", style = style, color = color)
        Text(text = text, style = style, color = color)
    }
}

// Для формул (с моноширинным шрифтом)
@Composable
fun HawkFormulaText(
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium.copy(
        fontFamily = FontFamily.Monospace
    ),
    color: Color = MaterialTheme.colorScheme.outline,
) {
    Text(text = text, style = style, color = color)
}

// Для подзаголовков (между Bold и Plain)
@Composable
fun HawkSubheaderText(
    text: String,
    style: TextStyle = MaterialTheme.typography.titleSmall,
    color: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.8f),
) {
    Text(text = text, style = style, color = color)
}

// Для выделения важных цифр
@Composable
fun HawkValueText(
    text: String,
    style: TextStyle = MaterialTheme.typography.bodyLarge.copy(
        fontWeight = FontWeight.SemiBold
    ),
    color: Color = MaterialTheme.colorScheme.primary,
) {
    Text(text = text, style = style, color = color)
}

// Для разделителей между секциями
@Composable
fun HawkDivider(
    color: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
    thickness: Dp = 1.dp,
) {
    HorizontalDivider(color = color, thickness = thickness)
}