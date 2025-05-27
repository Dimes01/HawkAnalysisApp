package hawk.analysis.app.utilities

import android.icu.math.BigDecimal
import hawk.analysis.app.dto.AccountInfo
import hawk.analysis.app.dto.AssetAnalyse
import hawk.analysis.app.dto.TokenInfo
import hawk.analysis.app.dto.UserInfo
import hawk.analysis.restlib.contracts.AccessLevel
import hawk.analysis.restlib.contracts.Account
import hawk.analysis.restlib.contracts.AccountStatus
import hawk.analysis.restlib.contracts.AccountType
import hawk.analysis.restlib.contracts.BrandData
import hawk.analysis.restlib.contracts.Currency
import hawk.analysis.restlib.contracts.FindInstrumentResponse
import hawk.analysis.restlib.contracts.InstrumentExchangeType
import hawk.analysis.restlib.contracts.InstrumentShort
import hawk.analysis.restlib.contracts.InstrumentType
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

val assetAnalyse = AssetAnalyse(
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

val findInstruments = FindInstrumentResponse(listOf(
    InstrumentShort(
        isin = "RU0009033591",
        figi = "BBG004RVFFC0",
        ticker = "TATN",
        classCode = "TQBR",
        instrumentType = "share",
        name = "Татнефть",
        uid = "88468f6c-c67a-4fb4-a006-53eed803883c",
        positionUid = "43ba384c-a484-4ee7-842d-59b80260fa3f",
        instrumentKind = InstrumentType.INSTRUMENT_TYPE_SHARE,
        apiTradeAvailableFlag = true,
        forIisFlag = true,
//        first1minCandleDate = Instant.parse("2018-03-07T18:33:00Z"),
//        first1dayCandleDate = Instant.parse("2001-12-07T07:00:00Z"),
        forQualInvestorFlag = false,
        weekendFlag = true,
        lot = 1
    ),
    InstrumentShort(
        isin = "RU0009091573",
        figi = "BBG00475KHX6",
        ticker = "TRNFP",
        classCode = "TQBR",
        instrumentType = "share",
        name = "Транснефть - привилегированные акции",
        uid = "653d47e9-dbd4-407a-a1c3-47f897df4694",
        positionUid = "d154098b-135b-4df9-ab3a-e719459debc3",
        instrumentKind = InstrumentType.INSTRUMENT_TYPE_SHARE,
        apiTradeAvailableFlag = true,
        forIisFlag = true,
//        first1minCandleDate = Instant.parse("2018-03-07T18:35:00Z"),
//        first1dayCandleDate = Instant.parse("2002-05-22T07:00:00Z"),
        forQualInvestorFlag = false,
        weekendFlag = true,
        lot = 1
    ),
    InstrumentShort(
        isin = "RU000A0J2Q06",
        figi = "BBG004731354",
        ticker = "ROSN",
        classCode = "TQBR",
        instrumentType = "share",
        name = "Роснефть",
        uid = "fd417230-19cf-4e7b-9623-f7c9ca18ec6b",
        positionUid = "0a0acc65-84db-4ffc-b359-faa00de99d41",
        instrumentKind = InstrumentType.INSTRUMENT_TYPE_SHARE,
        apiTradeAvailableFlag = true,
        forIisFlag = true,
//        first1minCandleDate = Instant.parse("2018-03-07T18:33:00Z"),
//        first1dayCandleDate = Instant.parse("2006-07-19T07:00:00Z"),
        forQualInvestorFlag = false,
        weekendFlag = true,
        lot = 1
    ),
    InstrumentShort(
        isin = "RU0009062467",
        figi = "BBG004S684M6",
        ticker = "SIBN",
        classCode = "TQBR",
        instrumentType = "share",
        name = "Газпром нефть",
        uid = "9ba367af-dfbd-4d9c-8730-4b1d5a47756e",
        positionUid = "fdf347d4-9262-474d-ae19-aedcd8c375a1",
        instrumentKind = InstrumentType.INSTRUMENT_TYPE_SHARE,
        apiTradeAvailableFlag = true,
        forIisFlag = true,
//        first1minCandleDate = Instant.parse("2018-03-07T18:33:00Z"),
//        first1dayCandleDate = Instant.parse("1999-09-06T00:00:00Z"),
        forQualInvestorFlag = false,
        weekendFlag = true,
        lot = 1
    ),
    InstrumentShort(
        isin = "RU0006944147",
        figi = "BBG004S68829",
        ticker = "TATNP",
        classCode = "TQBR",
        instrumentType = "share",
        name = "Татнефть - привилегированные акции",
        uid = "efdb54d3-2f92-44da-b7a3-8849e96039f6",
        positionUid = "25d48d77-b2bc-4617-ba87-eeaa06f2cf2c",
        instrumentKind = InstrumentType.INSTRUMENT_TYPE_SHARE,
        apiTradeAvailableFlag = true,
        forIisFlag = true,
//        first1minCandleDate = Instant.parse("2018-03-07T18:35:00Z"),
//        first1dayCandleDate = Instant.parse("2007-07-12T07:00:00Z"),
        forQualInvestorFlag = false,
        weekendFlag = true,
        lot = 1
    ),
    InstrumentShort(
        isin = "RU0007976965",
        figi = "BBG004S686N0",
        ticker = "BANEP",
        classCode = "TQBR",
        instrumentType = "share",
        name = "Башнефть - привилегированные акции",
        uid = "a5776620-1e2f-47ea-bbd6-06d8e4a236d8",
        positionUid = "aeaa8f4a-5149-49ed-8b9d-816b739bc882",
        instrumentKind = InstrumentType.INSTRUMENT_TYPE_SHARE,
        apiTradeAvailableFlag = true,
        forIisFlag = true,
//        first1minCandleDate = Instant.parse("2018-03-07T18:35:00Z"),
//        first1dayCandleDate = Instant.parse("2011-11-18T00:00:00Z"),
        forQualInvestorFlag = false,
        weekendFlag = true,
        lot = 1
    ),
    InstrumentShort(
        isin = "RU0007976957",
        figi = "BBG004S68758",
        ticker = "BANE",
        classCode = "TQBR",
        instrumentType = "share",
        name = "Башнефть",
        uid = "0a55e045-e9a6-42d2-ac55-29674634af2f",
        positionUid = "b37bbda4-1c2b-4929-9a38-6a6eeec6aa3d",
        instrumentKind = InstrumentType.INSTRUMENT_TYPE_SHARE,
        apiTradeAvailableFlag = true,
        forIisFlag = true,
//        first1minCandleDate = Instant.parse("2018-03-07T18:51:00Z"),
//        first1dayCandleDate = Instant.parse("2011-11-18T00:00:00Z"),
        forQualInvestorFlag = false,
        weekendFlag = true,
        lot = 1
    ),
))