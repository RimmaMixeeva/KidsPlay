package rimma.mixeeva.kidsplay.screens.server.models

import kotlinx.serialization.Serializable

@Serializable
data class AddChildRequest (
    val username: String,
    val avatarId: Int
)