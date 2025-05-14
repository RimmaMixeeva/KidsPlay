package rimma.mixeeva.kidsplay.data.server.models

import kotlinx.serialization.Serializable

@Serializable
data class AddChildRequest (
    val username: String,
    val avatarId: Int
)