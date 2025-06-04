package hawk.analysis.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ErrorMessage(text: String, modifier: Modifier = Modifier, textAlign: TextAlign = TextAlign.Center) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.error,
        textAlign = textAlign,
        modifier = modifier
    )
}

@Composable
fun HawkParameter(
    name: String,
    value: String,
    modifier: Modifier,
    color: Color = MaterialTheme.colorScheme.secondary,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.headlineMedium,
            color = color,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = color,
        )
    }
}

@Composable
fun HawkParameterRelative(
    name: String,
    value: String,
    valueRelative: String,
    modifier: Modifier,
    color: Color = MaterialTheme.colorScheme.secondary,
    colorRelative: Color = MaterialTheme.colorScheme.outline
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.headlineMedium,
            color = color,
        )
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                color = color,
            )
            Text(
                text = "$valueRelative%",
                style = MaterialTheme.typography.bodyMedium,
                color = colorRelative,
            )
        }
    }
}