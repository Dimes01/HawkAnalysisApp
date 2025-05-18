package hawk.analysis.app.utilities

import androidx.compose.ui.res.stringResource
import hawk.analysis.app.R
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.char

val dateFormat = DateTimeComponents.Format { year(); char('.'); monthNumber(); char('.'); dayOfMonth() }
val timeFormat = DateTimeComponents.Format { hour(); char(':'); minute(); char(':'); second() }
val dateTimeFormat = DateTimeComponents.Format {
    year(); char('.'); monthNumber(); char('.'); dayOfMonth()
    char(' ')
    hour(); char(':'); minute(); char(':'); second()
}

val cuurencies = HashMap<String, Int>().apply {
    this["rub"] = R.string.currency_rub
}