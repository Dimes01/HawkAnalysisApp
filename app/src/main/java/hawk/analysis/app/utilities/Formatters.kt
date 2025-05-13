package hawk.analysis.app.utilities

import androidx.compose.ui.res.stringResource
import hawk.analysis.app.R
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.char

val timeFormat = DateTimeComponents.Format { hour(); char(':'); minute(); char(':'); second() }

val cuurencies = HashMap<String, Int>().apply {
    this["rub"] = R.string.currency_rub
}