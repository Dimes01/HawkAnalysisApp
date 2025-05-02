package hawk.analysis.app.di

import hawk.analysis.app.services.AuthService
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single<AuthService>(named("dev_auth_service")) { AuthService("http://localhost:8080", get()) }
    single<AuthService>(named("prod_auth_service")) { AuthService("http://31.128.50.75:8080", get()) }
    single<HttpClient> { HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    } }
}