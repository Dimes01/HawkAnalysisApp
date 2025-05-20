package hawk.analysis.app.utilities

import android.icu.math.BigDecimal
import hawk.analysis.app.dto.AccountInfo
import hawk.analysis.app.dto.Analyse
import hawk.analysis.app.dto.TokenInfo
import hawk.analysis.app.dto.UserInfo
import hawk.analysis.restlib.contracts.AccessLevel
import hawk.analysis.restlib.contracts.Account
import hawk.analysis.restlib.contracts.AccountStatus
import hawk.analysis.restlib.contracts.AccountType
import hawk.analysis.restlib.contracts.BrandData
import hawk.analysis.restlib.contracts.Currency
import hawk.analysis.restlib.contracts.InstrumentExchangeType
import hawk.analysis.restlib.contracts.MoneyValue
import hawk.analysis.restlib.contracts.PortfolioPosition
import hawk.analysis.restlib.contracts.PortfolioResponse
import hawk.analysis.restlib.contracts.Quotation
import hawk.analysis.restlib.contracts.RealExchange
import hawk.analysis.restlib.contracts.SecurityTradingStatus
import hawk.analysis.restlib.contracts.Share
import hawk.analysis.restlib.contracts.ShareType
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
    benchmarkUid = "AFLT",
    updatedAt = Instant.parse("2025-01-01T12:34:56Z"),
)

val token = TokenInfo(1, 1, "", "Самый важный", Instant.parse("2022-03-02T00:00:00Z"), Instant.parse("1970-01-01T00:00:00Z"))

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
    nominal = MoneyValue(currency = "rub", units = 1, nano = 0),
    countryOfRisk = "",
    countryOfRiskName = "",
    tradingStatus = SecurityTradingStatus.SECURITY_TRADING_STATUS_NOT_AVAILABLE_FOR_TRADING,
    otcFlag = false,
    buyAvailableFlag = false,
    sellAvailableFlag = false,
    isoCurrencyName = "rub",
    minPriceIncrement = Quotation(units = 0, nano = 2500000),
    apiTradeAvailableFlag = false,
    uid = "a92e2e25-a698-45cc-a781-167cf465257c",
    realExchange = RealExchange.REAL_EXCHANGE_MOEX,
    positionUid = "33e24a92-aab0-409c-88b8-f2d57415b920",
    forIisFlag = true,
    forQualInvestorFlag = false,
    weekendFlag = false,
    blockedTcaFlag = false,
    brand = BrandData(logoName = "ruble.png", logoBaseColor = "#000000", textColor = "#ffffff"),
)

val shareLkoh = Share(
    figi = "BBG004731032",
    ticker = "LKOH",
    classCode = "TQBR",
    isin = "RU0009024277",
    lot = 1,
    currency = "rub",
    klong = Quotation(units = 2, nano = 0),
    kshort = Quotation(units = 2, nano = 0),
    dlong = Quotation(units = 0, nano = 181800000),
    dshort = Quotation(units = 0, nano = 200000000),
    dlongMin = Quotation(units = 0, nano = 125000000),
    dshortMin = Quotation(units = 0, nano = 142800000),
    shortEnabledFlag = true,
    name = "ЛУКОЙЛ",
    exchange = "moex_mrng_evng_e_wknd_dlr",
    ipoDate = Instant.parse("2003-06-25T00:00:00Z"),
    issueSize = "692865762".toLong(),
    countryOfRisk = "RU",
    countryOfRiskName = "Российская Федерация",
    sector = "energy",
    issueSizePlan = "850563255".toLong(),
    nominal = MoneyValue(currency = "rub", units = 0, nano = 25000000),
    tradingStatus = SecurityTradingStatus.SECURITY_TRADING_STATUS_NORMAL_TRADING,
    otcFlag = false,
    buyAvailableFlag = true,
    sellAvailableFlag = true,
    divYieldFlag = true,
    shareType = ShareType.SHARE_TYPE_COMMON,
    minPriceIncrement = Quotation(units = 0, nano = 500000000),
    apiTradeAvailableFlag = true,
    uid = "02cfdf61-6298-4c0f-a9ca-9cabc82afaf3",
    realExchange = RealExchange.REAL_EXCHANGE_MOEX,
    positionUid = "3213e01a-7bce-4946-b01e-5b0aaa3fc9b4",
    assetUid = "f898047a-dac8-4717-a40f-7f11211139ef",
    instrumentExchange = InstrumentExchangeType.INSTRUMENT_EXCHANGE_UNSPECIFIED,
    forIisFlag = true,
    forQualInvestorFlag = false,
    weekendFlag = true,
    blockedTcaFlag = false,
    liquidityFlag = true,
    first1minCandleDate = Instant.parse("2018-03-07T18:33:00Z"),
    first1dayCandleDate = Instant.parse("2000-01-04T07:00:00Z"),
    brand = BrandData(logoName = "RU0009024277.png", logoBaseColor = "#e31d24", textColor = "#ffffff"),
    dlongClient = Quotation(units = 0, nano = 181800000),
    dshortClient = Quotation(units = 0, nano = 200000000),
)

val shareNvtk = Share(
    figi = "BBG00475KKY8",
    ticker = "NVTK",
    classCode = "TQBR",
    isin = "RU000A0DKVS5",
    lot = 1,
    currency = "rub",
    klong = Quotation(units = 2, nano = 0),
    kshort = Quotation(units = 2, nano = 0),
    dlong = Quotation(units = 0, nano = 181800000),
    dshort = Quotation(units = 0, nano = 214400000),
    dlongMin = Quotation(units = 0, nano = 125000000),
    dshortMin = Quotation(units = 0, nano = 188600000),
    shortEnabledFlag = true,
    name = "НОВАТЭК",
    exchange = "moex_mrng_evng_e_wknd_dlr",
    ipoDate = Instant.parse("2006-07-20T00:00:00Z"),
    issueSize = 3036306000,
    countryOfRisk = "RU",
    countryOfRiskName = "Российская Федерация",
    sector = "energy",
    issueSizePlan = 3036306000,
    nominal = MoneyValue(currency = "rub", units = 0, nano = 100000000),
    tradingStatus = SecurityTradingStatus.SECURITY_TRADING_STATUS_NORMAL_TRADING,
    otcFlag = false,
    buyAvailableFlag = true,
    sellAvailableFlag = true,
    divYieldFlag = true,
    shareType = ShareType.SHARE_TYPE_COMMON,
    minPriceIncrement = Quotation(units = 0, nano = 200000000),
    apiTradeAvailableFlag = true,
    uid = "0da66728-6c30-44c4-9264-df8fac2467ee",
    realExchange = RealExchange.REAL_EXCHANGE_MOEX,
    positionUid = "e9aa66a5-573d-4b96-9ebc-7c4c3d520e44",
    assetUid = "2d850de1-466d-443d-b7fd-e78bf41146fb",
    instrumentExchange = InstrumentExchangeType.INSTRUMENT_EXCHANGE_UNSPECIFIED,
    forIisFlag = true,
    forQualInvestorFlag = false,
    weekendFlag = true,
    blockedTcaFlag = false,
    liquidityFlag = true,
    first1minCandleDate = Instant.parse("2018-03-07T18:34:00Z"),
    first1dayCandleDate = Instant.parse("2004-12-30T07:00:00Z"),
    brand = BrandData(logoName = "novatec.png", logoBaseColor = "#dde0e4", textColor = "#000000"),
    dlongClient = Quotation(units = 0, nano = 181800000),
    dshortClient = Quotation(units = 0, nano = 214400000)
)

val portfolioPositionShare = PortfolioPosition(
    figi = "BBG00475KKY8",
    instrumentType = "share",
    quantity = Quotation(units = 1, nano = 0),
    averagePositionPrice = MoneyValue(currency = "rub", units = 848, nano = 0),
    expectedYield = Quotation(units = 291, nano = 600000000),
    currentPrice = MoneyValue(currency = "rub", units = 1139, nano = 600000000),
    averagePositionPriceFifo = MoneyValue(currency = "rub", units = 848, nano = 0),
    blocked = false,
    blockedLots = Quotation(units = 0, nano = 0),
    positionUid = "e9aa66a5-573d-4b96-9ebc-7c4c3d520e44",
    instrumentUid = "0da66728-6c30-44c4-9264-df8fac2467ee",
    varMargin = MoneyValue(currency = "", units = 0, nano = 0),
    expectedYieldFifo = Quotation(units = 291, nano = 600000000),
    dailyYield = MoneyValue(currency = "rub", units = -17, nano = -600000000),
    ticker = "NVTK"
)

val portfolioPositionEtf = PortfolioPosition(
    figi = "BBG333333333",
    instrumentType = "etf",
    quantity = Quotation(units = 200, nano = 0),
    averagePositionPrice = MoneyValue(currency = "rub", units = 5, nano = 560000000),
    expectedYield = Quotation(units = 136, nano = 0),
    currentPrice = MoneyValue(currency = "rub", units = 6, nano = 240000000),
    averagePositionPriceFifo = MoneyValue(currency = "rub", units = 5, nano = 560000000),
    blocked = false,
    blockedLots = Quotation(units = 0, nano = 0),
    positionUid = "29888afc-181d-45ed-a00b-8fe55a9cec9b",
    instrumentUid = "9654c2dd-6993-427e-80fa-04e80a1cf4da",
    varMargin = MoneyValue(currency = "", units = 0, nano = 0),
    expectedYieldFifo = Quotation(units = 136, nano = 0),
    dailyYield = MoneyValue(currency = "rub", units = -8, nano = 0),
    ticker = "TMOS"
)

val portfolioPositionBond = PortfolioPosition(
    figi = "BBG01L5QP0V7",
    instrumentType = "bond",
    quantity = Quotation(units = 1, nano = 0),
    averagePositionPrice = MoneyValue(currency = "rub", units = 916, nano = 300000000),
    expectedYield = Quotation(units = 42, nano = 600000000),
    currentNkd = MoneyValue(currency = "rub", units = 10, nano = 730000000),
    currentPrice = MoneyValue(currency = "rub", units = 958, nano = 900000000),
    averagePositionPriceFifo = MoneyValue(currency = "rub", units = 916, nano = 300000000),
    blocked = false,
    blockedLots = Quotation(units = 0, nano = 0),
    positionUid = "3227299a-cc19-48fc-bc15-23d28c08e250",
    instrumentUid = "9e7afeb3-5ab6-42bd-b4f2-51d618228a88",
    varMargin = MoneyValue(currency = "", units = 0, nano = 0),
    expectedYieldFifo = Quotation(units = 42, nano = 600000000),
    dailyYield = MoneyValue(currency = "rub", units = -1, nano = -100000000),
    ticker = "RU000A107MM9"
)

val portfolioPositionCurrency = PortfolioPosition(
    figi = "RUB000UTSTOM",
    instrumentType = "currency",
    quantity = Quotation(units = 300, nano = 70000000),
    averagePositionPrice = MoneyValue(currency = "rub", units = 1, nano = 0),
    expectedYield = Quotation(units = 0, nano = 0),
    currentPrice = MoneyValue(currency = "rub", units = 1, nano = 0),
    averagePositionPriceFifo = MoneyValue(currency = "rub", units = 1, nano = 0),
    blocked = false,
    blockedLots = Quotation(units = 0, nano = 0),
    positionUid = "33e24a92-aab0-409c-88b8-f2d57415b920",
    instrumentUid = "a92e2e25-a698-45cc-a781-167cf465257c",
    varMargin = MoneyValue(currency = "", units = 0, nano = 0),
    expectedYieldFifo = Quotation(units = 0, nano = 0),
    dailyYield = MoneyValue(currency = "rub", units = 0, nano = 0),
    ticker = "RUB000UTSTOM"
)

val portfolio = PortfolioResponse(
    totalAmountShares = MoneyValue(currency = "rub", units = 1139, nano = 600000000),
    totalAmountBonds = MoneyValue(currency = "rub", units = 969, nano = 630000000),
    totalAmountEtf = MoneyValue(currency = "rub", units = 1248, nano = 0),
    totalAmountCurrencies = MoneyValue(currency = "rub", units = 300, nano = 70000000),
    totalAmountFutures = MoneyValue(currency = "rub", units = 0, nano = 0),
    expectedYield = Quotation(units = 14, nano = 750000000),
    positions = listOf(portfolioPositionShare, portfolioPositionEtf, portfolioPositionBond, portfolioPositionCurrency),
    accountId = "2155705052",
    totalAmountOptions = MoneyValue(currency = "rub", units = 0, nano = 0),
    totalAmountSp = MoneyValue(currency = "rub", units = 0, nano = 0),
    totalAmountPortfolio = MoneyValue(currency = "rub", units = 3657, nano = 300000000),
    virtualPositions = emptyList(),
    dailyYield = MoneyValue(currency = "rub", units = -26, nano = -700000000),
    dailyYieldRelative = Quotation(units = 0, nano = -720000000)
)

val analyse = Analyse(
    id = 1,
    accountId = accountAPI.id,
    securitiesId = shareNvtk.figi,
    dateFrom = Instant.parse("2025-05-19T14:07:00Z"),
    dateTo = Instant.parse("2025-05-19T14:07:00Z"),
    mean = 0.1,
    stdDev = 0.0,
    variation = 0.0,
    sharp = 0.0,
    sortino = 0.0,
    information = 0.0,
)