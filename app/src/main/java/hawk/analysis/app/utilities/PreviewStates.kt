package hawk.analysis.app.utilities

import android.icu.math.BigDecimal
import hawk.analysis.app.screens.HomeScreenState
import hawk.analysis.app.screens.MoneyState
import hawk.analysis.restlib.contracts.MoneyValue
import kotlinx.datetime.Instant

val state = HomeScreenState(
    lastUpdatedAt = Instant.parse("2022-03-02T09:12:34Z"),
    sum = BigDecimal.valueOf(333000),
    profit = BigDecimal.valueOf(1000),
    profitRelative = BigDecimal.valueOf(10),
    money = listOf(MoneyState(currencyRub, MoneyValue("RUB", 1, 0)))
)