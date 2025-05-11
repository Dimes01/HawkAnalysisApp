package hawk.analysis.restlib.utilities

import hawk.analysis.restlib.contracts.AccessLevel
import hawk.analysis.restlib.contracts.AccountStatus
import hawk.analysis.restlib.contracts.AccountType
import hawk.analysis.restlib.contracts.CurrencyRequest
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object AccessLevelSerializer : KSerializer<AccessLevel> {
    override val descriptor = PrimitiveSerialDescriptor(AccessLevel::class.qualifiedName!!, PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): AccessLevel = stringToAccessLevel[decoder.decodeString()] ?: AccessLevel.ACCOUNT_ACCESS_LEVEL_UNSPECIFIED
    override fun serialize(encoder: Encoder, value: AccessLevel) = encoder.encodeString(value.name)
}

object AccountStatusSerializer : KSerializer<AccountStatus> {
    override val descriptor = PrimitiveSerialDescriptor(AccountStatus::class.qualifiedName!!, PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): AccountStatus = stringToAccountStatus[decoder.decodeString()] ?: AccountStatus.ACCOUNT_STATUS_UNSPECIFIED
    override fun serialize(encoder: Encoder, value: AccountStatus) = encoder.encodeString(value.name)
}

object AccountTypeSerializer : KSerializer<AccountType> {
    override val descriptor = PrimitiveSerialDescriptor(AccountType::class.qualifiedName!!, PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): AccountType = stringToAccountType[decoder.decodeString()] ?: AccountType.ACCOUNT_TYPE_UNSPECIFIED
    override fun serialize(encoder: Encoder, value: AccountType) = encoder.encodeString(value.name)
}

object CurrencyRequestSerializer : KSerializer<CurrencyRequest> {
    override val descriptor = PrimitiveSerialDescriptor(CurrencyRequest::class.qualifiedName!!, PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): CurrencyRequest = CurrencyRequest.byName(decoder.decodeString()) ?: CurrencyRequest.RUB
    override fun serialize(encoder: Encoder, value: CurrencyRequest) = encoder.encodeString(value.name)
}