package hawk.analysis.app.ui.components

import android.icu.math.BigDecimal
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hawk.analysis.app.R
import hawk.analysis.app.ui.theme.negativeColor
import hawk.analysis.app.ui.theme.positiveColor

@Preview(widthDp = 440, heightDp = 956, showBackground = true)
@Composable
fun CommonInformationPreview() {
    CommonInformation(
        sum = BigDecimal.valueOf(333000),
        profit = BigDecimal.valueOf(10000),
        profitPercent = BigDecimal.valueOf(10),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    )
}

@Composable
fun CommonInformation(
    sum: BigDecimal,
    profit: BigDecimal,
    profitPercent: BigDecimal,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${sum.setScale(2)} ${stringResource(R.string.currency_rub)}",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
        ) {
            val compare = profit.compareTo(BigDecimal.ZERO)
            val color = if (compare == -1) negativeColor
                else if (compare == 0) MaterialTheme.colorScheme.outline
                else positiveColor
            Text(
                text = "${profit.setScale(2)} ${stringResource(R.string.currency_rub)}",
                style = MaterialTheme.typography.bodyLarge,
                color = color,
                textAlign = TextAlign.Right
            )
            VerticalDivider(color = MaterialTheme.colorScheme.outline)
            Text(
                text = "${profitPercent.setScale(2)} %",
                style = MaterialTheme.typography.bodyLarge,
                color = color,
                textAlign = TextAlign.Left
            )
        }
    }
}