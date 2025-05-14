package rimma.mixeeva.kidsplay.data.server.models

import kotlinx.serialization.Serializable

@Serializable
data class GetChildInfoResponse(
    val username: String,
    val nickname: String,
    val parentname: String,
    val avatarId: Int
)