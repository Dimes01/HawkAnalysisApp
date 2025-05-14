package hawk.analysis.app.tiapi

import hawk.analysis.restlib.contracts.Currency
import hawk.analysis.restlib.contracts.InstrumentIdType
import hawk.analysis.restlib.contracts.InstrumentRequest
import hawk.analysis.restlib.contracts.Share
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class InstrumentServiceTI(
    private val client: HttpClient,
    private val baseUrl: String,
) {
    suspend fun currencyByFigi(figi: String): Currency? {
        val request = InstrumentRequest(InstrumentIdType.INSTRUMENT_ID_TYPE_FIGI, "", figi)
        val response = client.post("$baseUrl/rest/tinkoff.public.invest.api.contract.v1.InstrumentsService/CurrencyBy") {
            bearerAuth(figi)
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return if (response.status.isSuccess()) response.body()
            else null
    }

    suspend fun shareByFigi(figi: String): Share? {
        val request = InstrumentRequest(InstrumentIdType.INSTRUMENT_ID_TYPE_FIGI, "", figi)
        val response = client.post("$baseUrl/rest/tinkoff.public.invest.api.contract.v1.InstrumentsService/ShareBy") {
            bearerAuth(figi)
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return if (response.status.isSuccess()) response.body()
        else null
    }
}