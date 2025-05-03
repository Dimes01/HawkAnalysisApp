package hawk.analysis.app.di

import hawk.analysis.app.services.AuthService
import hawk.analysis.app.services.TokenService
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val devModule = module {
    val urlApi = "http://10.0.2.2:8080"
    single<AuthService> { AuthService(urlApi, get()) }
    single<TokenService> { TokenService(urlApi, get()) }
}

val prodModule = module {
    val urlApi = "http://31.128.50.75:8080"
    single<AuthService> { AuthService(urlApi, get()) }
    single<TokenService> { TokenService(urlApi, get()) }
}

val commonModule = module {
    single<HttpClient> { HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    } }
}