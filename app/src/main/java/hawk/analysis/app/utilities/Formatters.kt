package hawk.analysis.app.utilities

import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.char

val timeFormat = DateTimeComponents.Format { hour(); char(':'); minute(); char(':'); second() }