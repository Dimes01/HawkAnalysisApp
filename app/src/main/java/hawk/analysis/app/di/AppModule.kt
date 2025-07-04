package hawk.analysis.app.di

import hawk.analysis.app.services.AccountService
import hawk.analysis.app.services.AnalyseService
import hawk.analysis.app.services.AuthService
import hawk.analysis.app.services.TokenService
import hawk.analysis.app.services.UserService
import hawk.analysis.app.tiapi.InstrumentServiceTI
import hawk.analysis.app.tiapi.OperationServiceTI
import hawk.analysis.app.tiapi.UserServiceTI
import hawk.analysis.app.viewmodels.SharedViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val urlApi = "urlApi"
const val urlTInvestApi = "urlTInvestApi"

val devModule = module {
    single<String>(named(urlApi)) { "http://10.0.2.2:8080" }
}

val prodModule = module {
    single<String>(named(urlApi)) { "http://31.128.50.75:8080" }
}

val commonModule = module {
    single<String>(named(urlTInvestApi)) { "https://invest-public-api.tinkoff.ru/rest" }
    single<AuthService> { AuthService(baseUrl = get(named(urlApi)), client = get()) }
    single<UserService> { UserService(baseUrl = get(named(urlApi)), client = get()) }
    single<TokenService> { TokenService(baseUrl = get(named(urlApi)), client = get()) }
    single<AccountService> { AccountService(baseUrl = get(named(urlApi)), client = get()) }
    single<AnalyseService> { AnalyseService(baseUrl = get(named(urlApi)), client = get()) }
    single<UserServiceTI> { UserServiceTI(baseUrl = get(named(urlTInvestApi)), client = get()) }
    single<OperationServiceTI> { OperationServiceTI(baseUrl = get(named(urlTInvestApi)), client = get()) }
    single<InstrumentServiceTI> { InstrumentServiceTI(baseUrl = get(named(urlTInvestApi)), client = get()) }
    singleOf(::SharedViewModel)
    single<HttpClient> { HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    } }
}