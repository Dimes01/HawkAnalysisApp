package hawk.analysis.app.utilities

import com.google.protobuf.Timestamp
import kotlinx.datetime.Instant
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.char

val timeFormat = DateTimeComponents.Format { hour(); char(':'); minute(); char(':'); second() }

fun Instant.toTimestamp(): Timestamp {
    return Timestamp.newBuilder().setSeconds(this.epochSeconds).setNanos(this.nanosecondsOfSecond).build()
}