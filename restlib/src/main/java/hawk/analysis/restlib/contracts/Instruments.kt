package hawk.analysis.restlib.contracts

import android.icu.math.BigDecimal
import hawk.analysis.restlib.utilities.BigDecimalSerializer
import hawk.analysis.restlib.utilities.InstrumentIdTypeSerializer
import hawk.analysis.restlib.utilities.RealExchangeSerializer
import hawk.analysis.restlib.utilities.SecurityTradingStatusSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class InstrumentRequest(
    @Serializable(with = InstrumentIdTypeSerializer::class)
    val idType: InstrumentIdType,
    val classCode: String,
    val id: String
)

@Serializable
data class InstrumentResponse<T>(
    val instrument: T
)

@Serializable
data class Currency(
    val figi: String,                           //FIGI-идентификатор инструмента.
    val ticker: String,                         //Тикер инструмента.
    val classCode: String,                      //Класс-код (секция торгов).
    val isin: String,                           //ISIN-идентификатор инструмента.
    val lot: Int,                               //Лотность инструмента. Возможно совершение операций только на количества ценной бумаги, кратные параметру lot. Подробнее.
    val currency: String,                       //Валюта расчетов.
//    val klong: Quotation,                       //Коэффициент ставки риска длинной позиции по клиенту. 2 – клиент со стандартным уровнем риска (КСУР); 1 – клиент с повышенным уровнем риска (КПУР).
//    val kshort: Quotation,                      //Коэффициент ставки риска короткой позиции по клиенту. 2 – клиент со стандартным уровнем риска (КСУР); 1 – клиент с повышенным уровнем риска (КПУР).
//    val dlong: Quotation,                       //Ставка риска начальной маржи для КСУР лонг. Подробнее про ставки риска.
//    val dshort: Quotation,                      //Ставка риска начальной маржи для КСУР шорт. Подробнее про ставки риска.
//    val dlongMin: Quotation,                    //Ставка риска начальной маржи для КПУР лонг. Подробнее про ставки риска.
//    val dshortMin: Quotation,                   //Ставка риска начальной маржи для КПУР шорт. Подробнее про ставки риска.
    val shortEnabledFlag: Boolean,              //Признак доступности для операций в шорт.
    val name: String,                           //Название инструмента.
    val exchange: String,                       //Tорговая площадка (секция биржи).
    val nominal: MoneyValue,                    //Номинал.
    val countryOfRisk: String,                  //Код страны риска — то есть страны, в которой компания ведет основной бизнес.
    val countryOfRiskName: String,              //Наименование страны риска — то есть страны, в которой компания ведет основной бизнес.
    @Serializable(with = SecurityTradingStatusSerializer::class)
    val tradingStatus: SecurityTradingStatus,   //Текущий режим торгов инструмента.
    val otcFlag: Boolean,                       //Флаг, используемый ранее для определения внебиржевых инструментов. На данный момент не используется для торгуемых через API инструментов. Может использоваться как фильтр для операций, совершавшихся некоторое время назад на ОТС площадке.
    val buyAvailableFlag: Boolean,              //Признак доступности для покупки.
    val sellAvailableFlag: Boolean,             //Признак доступности для продажи.
    val isoCurrencyName: String,                //Строковый ISO-код валюты.
    val minPriceIncrement: Quotation,           //Шаг цены.
    val apiTradeAvailableFlag: Boolean,         //Параметр указывает на возможность торговать инструментом через API.
    val uid: String,                            //Уникальный идентификатор инструмента.
    @Serializable(with = RealExchangeSerializer::class)
    val realExchange: RealExchange,             //Реальная площадка исполнения расчетов (биржа).
//    val dlongClient: Quotation,                 //Ставка риска в лонг с учетом текущего уровня риска портфеля клиента. Подробнее про ставки риска.
//    val dshortClient: Quotation,                //Ставка риска в шорт с учетом текущего уровня риска портфеля клиента. Подробнее про ставки риска.
    val positionUid: String,                    //Уникальный идентификатор позиции инструмента.
    val forIisFlag: Boolean,                    //Признак доступности для ИИС.
    val forQualInvestorFlag: Boolean,           //Флаг, отображающий доступность торговли инструментом только для квалифицированных инвесторов.
    val weekendFlag: Boolean,                   //Флаг, отображающий доступность торговли инструментом по выходным.
    val blockedTcaFlag: Boolean,                //Флаг заблокированного ТКС.
//    val first1minCandleDate: Instant,           //Дата первой минутной свечи.
//    val first1dayCandleDate: Instant,           //Дата первой дневной свечи.
    val brand: BrandData,                       //Информация о бренде.
)

@Serializable
data class Share(
    val figi: String,	                    // FIGI-идентификатор инструмента.
    val ticker: String,	                    // Тикер инструмента.
    val classCode: String,	                // Класс-код инструмента.
    val isin: String,	                    // ISIN-идентификатор инструмента.
    val lot: Int,	                        // Лотность инструмента. Возможно совершение операций только на количества ценной бумаги, кратные параметру lot. Подробнее.
    val currency: String,	                // Валюта расчетов.
    val dlong: Quotation,	                // Ставка риска начальной маржи для КСУР лонг. Подробнее про ставки риска.
    val dshort: Quotation,	                // Ставка риска начальной маржи для КСУР шорт. Подробнее про ставки риска.
    val dlongMin: Quotation,	            // Ставка риска начальной маржи для КПУР лонг. Подробнее про ставки риска.
    val dshortMin: Quotation,	            // Ставка риска начальной маржи для КПУР шорт. Подробнее про ставки риска.
    val shortEnabledFlag: Boolean,	        // Признак доступности для операций в шорт.
    val name: String,	                    // Название инструмента.
    val exchange: String,	                // Tорговая площадка (секция биржи).
    val countryOfRisk: String,	            // Код страны риска — то есть страны, в которой компания ведет основной бизнес.
    val countryOfRiskName: String,	        // Наименование страны риска — то есть страны, в которой компания ведет основной бизнес.
    @Serializable(with = SecurityTradingStatusSerializer::class)
    val tradingStatus: SecurityTradingStatus,   //Текущий режим торгов инструмента.
    val buyAvailableFlag: Boolean,	        // Признак доступности для покупки.
    val sellAvailableFlag: Boolean,	        // Признак доступности для продажи.
    val minPriceIncrement: Quotation,	    // Шаг цены.
    val apiTradeAvailableFlag: Boolean,	    // Параметр указывает на возможность торговать инструментом через API.
    val uid: String,	                    // Уникальный идентификатор инструмента.
    @Serializable(with = RealExchangeSerializer::class)
    val realExchange: RealExchange,         // Реальная площадка исполнения расчетов (биржа).
    val positionUid: String,	            // Уникальный идентификатор позиции инструмента.
    val assetUid: String,	                // Уникальный идентификатор актива.
    val forIisFlag: Boolean,	            // Признак доступности для ИИС.
    val forQualInvestorFlag: Boolean,	    // Флаг, отображающий доступность торговли инструментом только для квалифицированных инвесторов.
    val weekendFlag: Boolean,	            // Флаг, отображающий доступность торговли инструментом по выходным.
    val blockedTcaFlag: Boolean,	        // Флаг заблокированного ТКС.
    val liquidityFlag: Boolean,	            // Тип инструмента.
    val first1minCandleDate: Instant,	    // Дата первой минутной свечи.
    val first1dayCandleDate: Instant,	    // Дата первой дневной свечи.
    val brand: BrandData,	                // Информация о бренде.
    val dlongClient: Quotation,	        // Ставка риска в лонг с учетом текущего уровня риска портфеля клиента. Подробнее про ставки риска.
    val dshortClient: Quotation,          // Ставка риска в шорт с учетом текущего уровня риска портфеля клиента.
)

@Serializable
data class InstrumentShort(
    val isin: String,
    val figi: String,
    val ticker: String,
    val classCode: String,
    val instrumentType: String,
    val name: String,
    val uid: String,
    val positionUid: String,
    val instrumentKind: Int,
    val apiTradeAvailableFlag: Boolean,
    val forIisFlag: Boolean,
    val forQualInvestorFlag: Boolean,
    val weekendFlag: Boolean,
    val lot: Int,
)

enum class InstrumentIdType(val value: Int) {
    INSTRUMENT_ID_UNSPECIFIED(0),
    INSTRUMENT_ID_TYPE_FIGI(1),
    INSTRUMENT_ID_TYPE_TICKER(2),
    INSTRUMENT_ID_TYPE_UID(3),
    INSTRUMENT_ID_TYPE_POSITION_UID(4);

    companion object {
        fun byName(name: String): InstrumentIdType? = entries.firstOrNull { it.name == name }
    }
}

enum class RealExchange(val value: Int) {
    REAL_EXCHANGE_UNSPECIFIED(0),
    REAL_EXCHANGE_MOEX(1),
    REAL_EXCHANGE_RTS(2),
    REAL_EXCHANGE_OTC(3),
    REAL_EXCHANGE_DEALER(4);

    companion object {
        fun byName(name: String): RealExchange? = entries.firstOrNull { it.name == name }
    }
}

enum class SecurityTradingStatus(val value: Int) {
    SECURITY_TRADING_STATUS_UNSPECIFIED(0),
    SECURITY_TRADING_STATUS_NOT_AVAILABLE_FOR_TRADING(1),
    SECURITY_TRADING_STATUS_OPENING_PERIOD(2),
    SECURITY_TRADING_STATUS_CLOSING_PERIOD(3),
    SECURITY_TRADING_STATUS_BREAK_IN_TRADING(4),
    SECURITY_TRADING_STATUS_NORMAL_TRADING(5),
    SECURITY_TRADING_STATUS_CLOSING_AUCTION(6),
    SECURITY_TRADING_STATUS_DARK_POOL_AUCTION(7),
    SECURITY_TRADING_STATUS_DISCRETE_AUCTION(8),
    SECURITY_TRADING_STATUS_OPENING_AUCTION_PERIOD(9),
    SECURITY_TRADING_STATUS_TRADING_AT_CLOSING_AUCTION_PRICE(10),
    SECURITY_TRADING_STATUS_SESSION_ASSIGNED(11),
    SECURITY_TRADING_STATUS_SESSION_CLOSE(12),
    SECURITY_TRADING_STATUS_SESSION_OPEN(13),
    SECURITY_TRADING_STATUS_DEALER_NORMAL_TRADING(14),
    SECURITY_TRADING_STATUS_DEALER_BREAK_IN_TRADING(15),
    SECURITY_TRADING_STATUS_DEALER_NOT_AVAILABLE_FOR_TRADING(16);

    companion object {
        fun byName(name: String): SecurityTradingStatus? = entries.firstOrNull { it.name == name }
    }
}