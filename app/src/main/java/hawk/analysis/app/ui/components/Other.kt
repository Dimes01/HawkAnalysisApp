package hawk.analysis.app.ui.components

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HawkHorizontalDivider(
    thickness: Dp = 1.dp,
    color: Color = MaterialTheme.colorScheme.outline,
    modifier: Modifier = Modifier
) {
    HorizontalDivider(modifier, thickness, color)
}

@Composable
fun HawkVerticalDivider(
    thickness: Dp = 1.dp,
    color: Color = MaterialTheme.colorScheme.outline,
    modifier: Modifier = Modifier
) {
    VerticalDivider(modifier, thickness, color)
}