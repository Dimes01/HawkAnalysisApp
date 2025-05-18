package hawk.analysis.app.ui.components

import android.icu.math.BigDecimal
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hawk.analysis.app.R

@Preview(widthDp = 440, heightDp = 956, showBackground = true)
@Composable
fun MoneySectionPreview() {
    MoneySection(
        name = "Рубль",
        country = "Россия",
        money = BigDecimal.valueOf(75000),
        currency = stringResource(R.string.currency_rub),
        modifier = Modifier.fillMaxWidth().padding(10.dp)
    )
}

@Composable
fun MoneySection(
    name: String, country: String, money: BigDecimal, currency: String, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // TODO: Написать получение иконки
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = country,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Text(
                text = "${money.setScale(2)}",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
        VerticalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        Text(
            text = currency,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun HawkInfoSection(
    name: String,
    styleText: TextStyle = MaterialTheme.typography.displaySmall,
    colorText: Color = MaterialTheme.colorScheme.primary,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(5.dp),
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(10.dp))
        .padding(horizontal = 20.dp, vertical = 10.dp),
    content: @Composable () -> Unit,
) {
    Column(modifier = modifier, verticalArrangement = verticalArrangement) {
        Text(text = name, style = styleText, color = colorText)
        content()
    }
}