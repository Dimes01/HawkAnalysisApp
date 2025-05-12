package hawk.analysis.restlib.utilities

import android.icu.math.BigDecimal
import hawk.analysis.restlib.contracts.MoneyValue
import hawk.analysis.restlib.contracts.Quotation

fun MoneyValue.toBigDecimal(): BigDecimal {
    val unitsPart = BigDecimal.valueOf(units)
    val nanoPart = BigDecimal.valueOf(nano.toLong(), 9)
    return unitsPart.add(nanoPart)
}

fun Quotation.toBigDecimal(): BigDecimal {
    val unitsPart = BigDecimal.valueOf(units)
    val nanoPart = BigDecimal.valueOf(nano.toLong(), 9)
    return unitsPart.add(nanoPart)
}
