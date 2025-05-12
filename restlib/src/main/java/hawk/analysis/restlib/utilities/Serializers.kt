package hawk.analysis.restlib.utilities

import hawk.analysis.restlib.contracts.AccessLevel
import hawk.analysis.restlib.contracts.AccountStatus
import hawk.analysis.restlib.contracts.AccountType
import hawk.analysis.restlib.contracts.CurrencyRequest
import hawk.analysis.restlib.contracts.InstrumentIdType
import hawk.analysis.restlib.contracts.RealExchange
import hawk.analysis.restlib.contracts.SecurityTradingStatus
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object AccessLevelSerializer : KSerializer<AccessLevel> {
    override val descriptor = PrimitiveSerialDescriptor(AccessLevel::class.qualifiedName!!, PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): AccessLevel = AccessLevel.byName(decoder.decodeString()) ?: AccessLevel.ACCOUNT_ACCESS_LEVEL_UNSPECIFIED
    override fun serialize(encoder: Encoder, value: AccessLevel) = encoder.encodeString(value.name)
}

object AccountStatusSerializer : KSerializer<AccountStatus> {
    override val descriptor = PrimitiveSerialDescriptor(AccountStatus::class.qualifiedName!!, PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): AccountStatus = AccountStatus.byName(decoder.decodeString()) ?: AccountStatus.ACCOUNT_STATUS_UNSPECIFIED
    override fun serialize(encoder: Encoder, value: AccountStatus) = encoder.encodeString(value.name)
}

object AccountTypeSerializer : KSerializer<AccountType> {
    override val descriptor = PrimitiveSerialDescriptor(AccountType::class.qualifiedName!!, PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): AccountType = AccountType.byName(decoder.decodeString()) ?: AccountType.ACCOUNT_TYPE_UNSPECIFIED
    override fun serialize(encoder: Encoder, value: AccountType) = encoder.encodeString(value.name)
}

object CurrencyRequestSerializer : KSerializer<CurrencyRequest> {
    override val descriptor = PrimitiveSerialDescriptor(CurrencyRequest::class.qualifiedName!!, PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): CurrencyRequest = CurrencyRequest.byName(decoder.decodeString()) ?: CurrencyRequest.RUB
    override fun serialize(encoder: Encoder, value: CurrencyRequest) = encoder.encodeString(value.name)
}

object InstrumentIdTypeSerializer : KSerializer<InstrumentIdType> {
    override val descriptor = PrimitiveSerialDescriptor(InstrumentIdType::class.qualifiedName!!, PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): InstrumentIdType = InstrumentIdType.byName(decoder.decodeString()) ?: InstrumentIdType.INSTRUMENT_ID_UNSPECIFIED
    override fun serialize(encoder: Encoder, value: InstrumentIdType) = encoder.encodeString(value.name)
}

object RealExchangeSerializer : KSerializer<RealExchange> {
    override val descriptor = PrimitiveSerialDescriptor(RealExchange::class.qualifiedName!!, PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): RealExchange = RealExchange.byName(decoder.decodeString()) ?: RealExchange.REAL_EXCHANGE_UNSPECIFIED
    override fun serialize(encoder: Encoder, value: RealExchange) = encoder.encodeString(value.name)
}

object SecurityTradingStatusSerializer : KSerializer<SecurityTradingStatus> {
    override val descriptor = PrimitiveSerialDescriptor(SecurityTradingStatus::class.qualifiedName!!, PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): SecurityTradingStatus = SecurityTradingStatus.byName(decoder.decodeString())
        ?: SecurityTradingStatus.SECURITY_TRADING_STATUS_UNSPECIFIED
    override fun serialize(encoder: Encoder, value: SecurityTradingStatus) = encoder.encodeString(value.name)
}