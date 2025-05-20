package hawk.analysis.restlib.contracts

import hawk.analysis.restlib.utilities.CurrencyRequestSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class PortfolioRequest(
    val accountId: String,
    @Serializable(with = CurrencyRequestSerializer::class)
    val currency: CurrencyRequest
)

@Serializable
data class PortfolioResponse(
    val totalAmountShares: MoneyValue,
    val totalAmountBonds: MoneyValue,
    val totalAmountEtf: MoneyValue,
    val totalAmountCurrencies: MoneyValue,
    val totalAmountFutures: MoneyValue,
    val expectedYield: Quotation,
    val positions: List<PortfolioPosition>,
    val accountId: String,
    val totalAmountOptions: MoneyValue,
    val totalAmountSp: MoneyValue,
    val totalAmountPortfolio: MoneyValue,
    val virtualPositions: List<VirtualPortfolioPosition>,
    val dailyYield: MoneyValue,
    val dailyYieldRelative: Quotation,
)

@Serializable
data class PositionsRequest(
    val accountId: String
)

@Serializable
data class PositionsResponse(
    val money: List<MoneyValue>,
    val blocked: List<MoneyValue>,
    val securities: List<PositionsSecurities>,
    val limitsLoadingInProgress: Boolean,
    val accountId: String,
)

@Serializable
data class PortfolioPosition (
    val figi: String,
    val instrumentType: String,
    val quantity: Quotation,
    val averagePositionPrice: MoneyValue,
    val expectedYield: Quotation,
    val currentNkd: MoneyValue? = null,
    val currentPrice: MoneyValue,
    val averagePositionPriceFifo: MoneyValue,
    val blocked: Boolean,
    val blockedLots: Quotation,
    val positionUid: String,
    val instrumentUid: String,
    val varMargin: MoneyValue,
    val expectedYieldFifo: Quotation,
    val dailyYield: MoneyValue,
    val ticker: String,
)

@Serializable
data class VirtualPortfolioPosition(
    val positionUid: String,
    val instrumentUid: String,
    val figi: String,
    val instrumentType: String,
    val quantity: Quotation,
    val averagePositionPrice: MoneyValue,
    val expectedYield: Quotation,
    val expectedYieldFifo: Quotation,
    val expireDate: Instant,
    val currentPrice: MoneyValue,
    val averagePositionPriceFifo: MoneyValue,
    val dailyYield : MoneyValue,
    val ticker: String,
)

@Serializable
data class PositionsSecurities(
    val figi: String,
    val blocked: Long,
    val balance: Long,
    val positionUid: String,
    val instrumentUid: String,
    val ticker: String,
    val exchangeBlocked: Boolean,
    val instrumentType: String,
)

enum class CurrencyRequest(val value: Int) {
    RUB(0), USD(1), EUR(2);

    companion object {
        fun byName(name: String): CurrencyRequest? = entries.firstOrNull { it.name == name }
    }
}