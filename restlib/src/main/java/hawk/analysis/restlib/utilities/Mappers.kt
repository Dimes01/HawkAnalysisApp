package hawk.analysis.restlib.utilities

import android.icu.math.BigDecimal
import android.icu.math.MathContext
import hawk.analysis.restlib.contracts.MoneyValue
import hawk.analysis.restlib.contracts.Quotation

fun MoneyValue.toBigDecimal(): BigDecimal {
    val unitsPart = BigDecimal.valueOf(units)
    val nanoPart = BigDecimal.valueOf(nano.toLong(), 9)
    return unitsPart.add(nanoPart)
}

fun MoneyValue.toBigDecimal(scale: Int, round: Int = MathContext.ROUND_HALF_UP): BigDecimal {
    val unitsPart = BigDecimal.valueOf(units)
    val nanoPart = BigDecimal.valueOf(nano.toLong(), 9)
    return unitsPart.add(nanoPart).setScale(scale, round)
}

fun Quotation.toBigDecimal(): BigDecimal {
    val unitsPart = BigDecimal.valueOf(units)
    val nanoPart = BigDecimal.valueOf(nano.toLong(), 9)
    return unitsPart.add(nanoPart)
}

fun Quotation.toBigDecimal(scale: Int, round: Int = MathContext.ROUND_HALF_UP): BigDecimal {
    val unitsPart = BigDecimal.valueOf(units)
    val nanoPart = BigDecimal.valueOf(nano.toLong(), 9)
    return unitsPart.add(nanoPart).setScale(scale, round)
}

val categoryTranslations = mapOf(
    // Основные категории (топ-10)
    "consumer" to "Потребительские товары",
    "it" to "IT",
    "health_care" to "Здравоохранение",
    "financial" to "Финансы",
    "industrials" to "Промышленность",
    "materials" to "Материалы",
    "energy" to "Энергетика",
    "telecom" to "Телеком",
    "real_estate" to "Недвижимость",
    "utilities" to "Коммунальные услуги",

//    // Категории для "Другое"
//    "" to "Не указано",
//    "other" to "Прочее",
//    "green_buildings" to "Зелёные здания",
//    "ecomaterials" to "Экоматериалы",
//    "green_energy" to "Зелёная энергетика",
//    "electrocars" to "Электромобили"
)