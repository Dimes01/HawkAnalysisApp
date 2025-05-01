package hawk.analysis.app.dto

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

@Serializable
data class Share(
    val figi: String,	                    // FIGI-идентификатор инструмента.
    val ticker: String,	                    // Тикер инструмента.
    val classCode: String,	                // Класс-код инструмента.
    val isin: String,	                    // ISIN-идентификатор инструмента.
    val lot: Int,	                        // Лотность инструмента. Возможно совершение операций только на количества ценной бумаги, кратные параметру lot. Подробнее.
    val currency: String,	                // Валюта расчетов.
    @Serializable(with = BigDecimalSerializer::class)
    val dlong: BigDecimal,	                // Ставка риска начальной маржи для КСУР лонг. Подробнее про ставки риска.
    @Serializable(with = BigDecimalSerializer::class)
    val dshort: BigDecimal,	                // Ставка риска начальной маржи для КСУР шорт. Подробнее про ставки риска.
    @Serializable(with = BigDecimalSerializer::class)
    val dlongMin: BigDecimal,	            // Ставка риска начальной маржи для КПУР лонг. Подробнее про ставки риска.
    @Serializable(with = BigDecimalSerializer::class)
    val dshortMin: BigDecimal,	            // Ставка риска начальной маржи для КПУР шорт. Подробнее про ставки риска.
    val shortEnabledFlag: Boolean,	        // Признак доступности для операций в шорт.
    val name: String,	                    // Название инструмента.
    val exchange: String,	                // Tорговая площадка (секция биржи).
    val countryOfRisk: String,	            // Код страны риска — то есть страны, в которой компания ведет основной бизнес.
    val countryOfRiskName: String,	        // Наименование страны риска — то есть страны, в которой компания ведет основной бизнес.
    val instrumentType: Int,	            // Тип инструмента.
    val tradingStatus: Int,	                // Текущий режим торгов инструмента.
    val buyAvailableFlag: Boolean,	        // Признак доступности для покупки.
    val sellAvailableFlag: Boolean,	        // Признак доступности для продажи.
    @Serializable(with = BigDecimalSerializer::class)
    val minPriceIncrement: BigDecimal,	    // Шаг цены.
    val apiTradeAvailableFlag: Boolean,	    // Параметр указывает на возможность торговать инструментом через API.
    val uid: String,	                    // Уникальный идентификатор инструмента.
    val realExchange: Int,	                // Реальная площадка исполнения расчетов (биржа).
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
    @Serializable(with = BigDecimalSerializer::class)
    val dlongClient: BigDecimal,	        // Ставка риска в лонг с учетом текущего уровня риска портфеля клиента. Подробнее про ставки риска.
    @Serializable(with = BigDecimalSerializer::class)
    val dshortClient: BigDecimal,          // Ставка риска в шорт с учетом текущего уровня риска портфеля клиента.
)

@Serializable
data class BrandData(
    val logoName: String,
    val logoBaseColor: String,
    val textColor: String,
)