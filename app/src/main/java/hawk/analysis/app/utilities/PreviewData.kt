package hawk.analysis.app.utilities

import android.icu.math.BigDecimal
import hawk.analysis.app.dto.AccountInfo
import hawk.analysis.app.dto.UserInfo
import hawk.analysis.restlib.contracts.AccessLevel
import hawk.analysis.restlib.contracts.Account
import hawk.analysis.restlib.contracts.AccountStatus
import hawk.analysis.restlib.contracts.AccountType
import hawk.analysis.restlib.contracts.BrandData
import hawk.analysis.restlib.contracts.Currency
import hawk.analysis.restlib.contracts.MoneyValue
import hawk.analysis.restlib.contracts.Quotation
import hawk.analysis.restlib.contracts.RealExchange
import hawk.analysis.restlib.contracts.SecurityTradingStatus
import kotlinx.datetime.Instant

val userAPI = UserInfo(
    id = 1,
    name = "Тестовый Т.Т.",
    email = "test@test.com",
    updatedAt = Instant.parse("2025-01-01T12:34:56Z"),
    createdAt = Instant.parse("2025-01-01T01:00:00Z"),
)

val accountTI = Account(
    id = "2156732337",
    type = AccountType.ACCOUNT_TYPE_INVEST_BOX,
    name = "Брокерский счет",
    status = AccountStatus.ACCOUNT_STATUS_OPEN,
    openedDate = Instant.parse("2022-03-02T00:00:00Z"),
    closedDate = Instant.parse("1970-01-01T00:00:00Z"),
    accessLevel = AccessLevel.ACCOUNT_ACCESS_LEVEL_READ_ONLY,
)

val accountAPI = AccountInfo(
    id = accountTI.id,
    userId = userAPI.id,
    type = accountTI.type.value,
    name = accountTI.name,
    status = accountTI.status.value,
    openedDate = accountTI.openedDate,
    closedDate = accountTI.closedDate,
    accessLevel = accountTI.accessLevel.value,
    riskFree = BigDecimal("0.1"),
    tickerBenchmark = "AFLT",
    updatedAt = Instant.parse("2025-01-01T12:34:56Z"),
)

val currencyRub = Currency(
    figi = "RUB000UTSTOM",
    ticker = "RUB000UTSTOM",
    classCode = "CETS",
    isin = "",
    lot = 1,
    currency = "rub",
    shortEnabledFlag = false,
    name = "Российский рубль",
    exchange = "FX",
    nominal = MoneyValue(
        currency = "rub",
        units = 1,
        nano = 0
    ),
    countryOfRisk = "",
    countryOfRiskName = "",
    tradingStatus = SecurityTradingStatus.SECURITY_TRADING_STATUS_NOT_AVAILABLE_FOR_TRADING,
    otcFlag = false,
    buyAvailableFlag = false,
    sellAvailableFlag = false,
    isoCurrencyName = "rub",
    minPriceIncrement = Quotation(
        units = 0,
        nano = 2500000
    ),
    apiTradeAvailableFlag = false,
    uid = "a92e2e25-a698-45cc-a781-167cf465257c",
    realExchange = RealExchange.REAL_EXCHANGE_MOEX,
    positionUid = "33e24a92-aab0-409c-88b8-f2d57415b920",
    forIisFlag = true,
    forQualInvestorFlag = false,
    weekendFlag = false,
    blockedTcaFlag = false,
    brand = BrandData(
        logoName = "ruble.png",
        logoBaseColor = "#000000",
        textColor = "#ffffff"
    ),
)