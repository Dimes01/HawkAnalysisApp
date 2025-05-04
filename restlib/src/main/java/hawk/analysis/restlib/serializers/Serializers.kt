package hawk.analysis.restlib.serializers

import hawk.analysis.restlib.enums.AccessLevel
import hawk.analysis.restlib.enums.AccountStatus
import hawk.analysis.restlib.enums.AccountType
import hawk.analysis.restlib.utilities.stringToAccessLevel
import hawk.analysis.restlib.utilities.stringToAccountStatus
import hawk.analysis.restlib.utilities.stringToAccountType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object AccessLevelSerializer : KSerializer<AccessLevel> {
    override val descriptor = PrimitiveSerialDescriptor(AccessLevel::class.qualifiedName!!, PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): AccessLevel = stringToAccessLevel[decoder.decodeString()] ?: AccessLevel.ACCOUNT_ACCESS_LEVEL_UNSPECIFIED
    override fun serialize(encoder: Encoder, value: AccessLevel) = encoder.encodeString(value.toString())
}

object AccountStatusSerializer : KSerializer<AccountStatus> {
    override val descriptor = PrimitiveSerialDescriptor(AccountStatus::class.qualifiedName!!, PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): AccountStatus = stringToAccountStatus[decoder.decodeString()] ?: AccountStatus.ACCOUNT_STATUS_UNSPECIFIED
    override fun serialize(encoder: Encoder, value: AccountStatus) = encoder.encodeString(value.toString())
}

object AccountTypeSerializer : KSerializer<AccountType> {
    override val descriptor = PrimitiveSerialDescriptor(AccountType::class.qualifiedName!!, PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): AccountType = stringToAccountType[decoder.decodeString()] ?: AccountType.ACCOUNT_TYPE_UNSPECIFIED
    override fun serialize(encoder: Encoder, value: AccountType) = encoder.encodeString(value.toString())
}