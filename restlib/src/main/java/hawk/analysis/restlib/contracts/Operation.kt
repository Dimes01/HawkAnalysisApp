package hawk.analysis.restlib.contracts

import hawk.analysis.restlib.utilities.CurrencyRequestSerializer
import hawk.analysis.restlib.utilities.OperationStateSerializer
import hawk.analysis.restlib.utilities.OperationTypeSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class OperationRequest(
    val accountId: String,
    val from: Instant,
    val to: Instant,
    @Serializable(with = OperationStateSerializer::class)
    val state: OperationState,
    val figi: String
)

@Serializable
data class OperationResponse(
    val operations: List<Operation>
)

@Serializable
data class Operation(
    val id: String,                                 //Идентификатор операции
    val parent_operation_id: String,                //Идентификатор родительской операции
    val currency: String,                           //Валюта операции
    val payment: MoneyValue,                        //Сумма операции
    val price: MoneyValue,                          //Цена операции за 1 инструмент. Чтобы получить стоимость лота, нужно умножить на лотность инструмента
    @Serializable(with = OperationStateSerializer::class)
    val state: OperationState,                      //Статус операции
    val quantity: Long,                             //Количество единиц инструмента
    val quantity_rest: Long,                        //Неисполненный остаток по сделке
    val figi: String,                               //FIGI-идентификатор инструмента, связанного с операцией
    val instrument_type: String,                    //Тип инструмента. Возможные значения
    val date: Instant,                              //Дата и время операции в формате часовом поясе UTC
    val type: String,                               //Текстовое описание типа операции
    @Serializable(with = OperationTypeSerializer::class)
    val operation_type: OperationType,              //Тип операции
    val trades: List<OperationTrade>,               //Массив сделок
    val asset_uid: String,                          //Идентификатор актив
    val position_uid: String,                       //Уникальный идентификатор позиции
    val instrument_uid: String,                     //Уникальный идентификатор инструмента
    val child_operations: List<ChildOperationItem>, //Массив дочерних операций
)

@Serializable
data class OperationTrade(
    val tradeId: String,
    val dateTime: Instant,
    val quantity: Long,
    val price: MoneyValue
)

@Serializable
data class ChildOperationItem(
    val instrumentUid: String,
    val payment: MoneyValue
)

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

enum class OperationState(val value: Int) {
    OPERATION_STATE_UNSPECIFIED(0),
    OPERATION_STATE_EXECUTED(1),
    OPERATION_STATE_CANCELED(2),
    OPERATION_STATE_PROGRESS(3);

    companion object {
        fun byName(name: String): OperationState? = entries.firstOrNull { it.name == name }
    }
}

enum class OperationType(val value: Int) {
    OPERATION_TYPE_UNSPECIFIED(0),                  // Тип операции не определен.
    OPERATION_TYPE_INPUT(1),                        // Пополнение брокерского счета.
    OPERATION_TYPE_BOND_TAX(2),                     // Удержание НДФЛ по купонам.
    OPERATION_TYPE_OUTPUT_SECURITIES(3),            // Вывод ЦБ.
    OPERATION_TYPE_OVERNIGHT(4),                    // Доход по сделке РЕПО овернайт.
    OPERATION_TYPE_TAX(5),                          // Удержание налога.
    OPERATION_TYPE_BOND_REPAYMENT_FULL(6),          // Полное погашение облигаций.
    OPERATION_TYPE_SELL_CARD(7),                    // Продажа ЦБ с карты.
    OPERATION_TYPE_DIVIDEND_TAX(8),                 // Удержание налога по дивидендам.
    OPERATION_TYPE_OUTPUT(9),                       // Вывод денежных средств.
    OPERATION_TYPE_BOND_REPAYMENT(10),              // Частичное погашение облигаций.
    OPERATION_TYPE_TAX_CORRECTION(11),              // Корректировка налога.
    OPERATION_TYPE_SERVICE_FEE(12),                 // Удержание комиссии за обслуживание брокерского счета.
    OPERATION_TYPE_BENEFIT_TAX(13),                 // Удержание налога за материальную выгоду.
    OPERATION_TYPE_MARGIN_FEE(14),                  // Удержание комиссии за непокрытую позицию.
    OPERATION_TYPE_BUY(15),                         // Покупка ЦБ.
    OPERATION_TYPE_BUY_CARD(16),                    // Покупка ЦБ с карты.
    OPERATION_TYPE_INPUT_SECURITIES(17),            // Перевод ценных бумаг из другого депозитария.
    OPERATION_TYPE_SELL_MARGIN(18),                 // Продажа в результате Margin-call.
    OPERATION_TYPE_BROKER_FEE(19),                  // Удержание комиссии за операцию.
    OPERATION_TYPE_BUY_MARGIN(20),                  // Покупка в результате Margin-call.
    OPERATION_TYPE_DIVIDEND(21),                    // Выплата дивидендов.
    OPERATION_TYPE_SELL(22),                        // Продажа ЦБ.
    OPERATION_TYPE_COUPON(23),                      // Выплата купонов.
    OPERATION_TYPE_SUCCESS_FEE(24),                 // Удержание комиссии SuccessFee.
    OPERATION_TYPE_DIVIDEND_TRANSFER(25),           // Передача дивидендного дохода.
    OPERATION_TYPE_ACCRUING_VARMARGIN(26),          // Зачисление вариационной маржи.
    OPERATION_TYPE_WRITING_OFF_VARMARGIN(27),       // Списание вариационной маржи.
    OPERATION_TYPE_DELIVERY_BUY(28),                // Покупка в рамках экспирации фьючерсного контракта.
    OPERATION_TYPE_DELIVERY_SELL(29),               // Продажа в рамках экспирации фьючерсного контракта.
    OPERATION_TYPE_TRACK_MFEE(30),                  // Комиссия за управление по счету автоследования.
    OPERATION_TYPE_TRACK_PFEE(31),                  // Комиссия за результат по счету автоследования.
    OPERATION_TYPE_TAX_PROGRESSIVE(32),             // Удержание налога по ставке 15%.
    OPERATION_TYPE_BOND_TAX_PROGRESSIVE(33),        // Удержание налога по купонам по ставке 15%.
    OPERATION_TYPE_DIVIDEND_TAX_PROGRESSIVE(34),    // Удержание налога по дивидендам по ставке 15%.
    OPERATION_TYPE_BENEFIT_TAX_PROGRESSIVE(35),     // Удержание налога за материальную выгоду по ставке 15%.
    OPERATION_TYPE_TAX_CORRECTION_PROGRESSIVE(36),  // Корректировка налога по ставке 15%.
    OPERATION_TYPE_TAX_REPO_PROGRESSIVE(37),        // Удержание налога за возмещение по сделкам РЕПО по ставке 15%.
    OPERATION_TYPE_TAX_REPO(38),                    // Удержание налога за возмещение по сделкам РЕПО.
    OPERATION_TYPE_TAX_REPO_HOLD(39),               // Удержание налога по сделкам РЕПО.
    OPERATION_TYPE_TAX_REPO_REFUND(40),             // Возврат налога по сделкам РЕПО.
    OPERATION_TYPE_TAX_REPO_HOLD_PROGRESSIVE(41),   // Удержание налога по сделкам РЕПО по ставке 15%.
    OPERATION_TYPE_TAX_REPO_REFUND_PROGRESSIVE(42), // Возврат налога по сделкам РЕПО по ставке 15%.
    OPERATION_TYPE_DIV_EXT(43),                     // Выплата дивидендов на карту.
    OPERATION_TYPE_TAX_CORRECTION_COUPON(44),       // Корректировка налога по купонам.
    OPERATION_TYPE_CASH_FEE(45),                    // Комиссия за валютный остаток.
    OPERATION_TYPE_OUT_FEE(46),                     // Комиссия за вывод валюты с брокерского счета.
    OPERATION_TYPE_OUT_STAMP_DUTY(47),              // Гербовый сбор.
    OPERATION_TYPE_OUTPUT_SWIFT(50),                // SWIFT-перевод.
    OPERATION_TYPE_INPUT_SWIFT(51),                 // SWIFT-перевод.
    OPERATION_TYPE_OUTPUT_ACQUIRING(53),            // Перевод на карту.
    OPERATION_TYPE_INPUT_ACQUIRING(54),             // Перевод с карты.
    OPERATION_TYPE_OUTPUT_PENALTY(55),              // Комиссия за вывод средств.
    OPERATION_TYPE_ADVICE_FEE(56),                  // Списание оплаты за сервис Советов.
    OPERATION_TYPE_TRANS_IIS_BS(57),                // Перевод ценных бумаг с ИИС на брокерский счет.
    OPERATION_TYPE_TRANS_BS_BS(58),                 // Перевод ценных бумаг с одного брокерского счета на другой.
    OPERATION_TYPE_OUT_MULTI(59),                   // Вывод денежных средств со счета.
    OPERATION_TYPE_INP_MULTI(60),                   // Пополнение денежных средств со счета.
    OPERATION_TYPE_OVER_PLACEMENT(61),              // Размещение биржевого овернайта.
    OPERATION_TYPE_OVER_COM(62),                    // Списание комиссии.
    OPERATION_TYPE_OVER_INCOME(63),                 // Доход от оверанайта.
    OPERATION_TYPE_OPTION_EXPIRATION(64),           // Экспирация опциона.
    OPERATION_TYPE_FUTURE_EXPIRATION(65);           // Экспирация фьючерса.

    companion object {
        fun byName(name: String): OperationType? = OperationType.entries.firstOrNull { it.name == name }
    }
}

enum class CurrencyRequest(val value: Int) {
    RUB(0), USD(1), EUR(2);

    companion object {
        fun byName(name: String): CurrencyRequest? = entries.firstOrNull { it.name == name }
    }
}