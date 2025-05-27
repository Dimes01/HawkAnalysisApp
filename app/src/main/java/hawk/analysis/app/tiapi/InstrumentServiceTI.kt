package hawk.analysis.app.tiapi

import hawk.analysis.restlib.contracts.Currency
import hawk.analysis.restlib.contracts.FindInstrumentRequest
import hawk.analysis.restlib.contracts.FindInstrumentResponse
import hawk.analysis.restlib.contracts.InstrumentResponse
import hawk.analysis.restlib.contracts.InstrumentIdType
import hawk.analysis.restlib.contracts.InstrumentRequest
import hawk.analysis.restlib.contracts.InstrumentType
import hawk.analysis.restlib.contracts.Share
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class InstrumentServiceTI(
    private val client: HttpClient,
    private val baseUrl: String,
) {
    suspend fun currencyByFigi(authToken: String, figi: String): InstrumentResponse<Currency>? {
        val request = InstrumentRequest(InstrumentIdType.INSTRUMENT_ID_TYPE_FIGI, "", figi)
        val response = client.post("$baseUrl/tinkoff.public.invest.api.contract.v1.InstrumentsService/CurrencyBy") {
            bearerAuth(authToken)
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        if (response.status.isSuccess()) return response.body()
        println(response.bodyAsText())
        return null
    }

    suspend fun shareByFigi(authToken: String, figi: String): InstrumentResponse<Share>? {
        val request = InstrumentRequest(InstrumentIdType.INSTRUMENT_ID_TYPE_FIGI, "", figi)
        val response = client.post("$baseUrl/tinkoff.public.invest.api.contract.v1.InstrumentsService/ShareBy") {
            bearerAuth(authToken)
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        if (response.status.isSuccess()) return response.body()
        println(response.bodyAsText())
        return null
    }

    suspend fun findInstruments(authToken: String, query: String): FindInstrumentResponse? {
        val request = FindInstrumentRequest(query = query, instrumentKind = InstrumentType.INSTRUMENT_TYPE_SHARE, apiTradeAvailableFlag = true)
        val response = client.post("$baseUrl/tinkoff.public.invest.api.contract.v1.InstrumentsService/FindInstrument") {
            bearerAuth(authToken)
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        if (response.status.isSuccess()) return response.body()
        println(response.bodyAsText())
        return null
    }
}