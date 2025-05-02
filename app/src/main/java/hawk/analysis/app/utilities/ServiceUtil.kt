package hawk.analysis.app.utilities

import io.ktor.client.request.header
import io.ktor.http.HttpMessageBuilder

fun HttpMessageBuilder.authHeader(token: String) = header("Authorization", "Bearer $token")