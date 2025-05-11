package hawk.analysis.app.screens

import android.icu.math.BigDecimal
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class HomeScreenState(
    val lastUpdatedAt: Instant = Clock.System.now(),
    val sum: BigDecimal = BigDecimal.ZERO,
    val profit: BigDecimal = BigDecimal.ZERO,
    val profitRelative: BigDecimal = BigDecimal.ZERO,
)