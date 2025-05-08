package rimma.mixeeva.kidsplay.screens.server.models

import kotlinx.serialization.Serializable

@Serializable
data class GetChildInfoResponse(
    val username: String,
    val nickname: String,
    val parentname: String,
    val avatarId: Int
)