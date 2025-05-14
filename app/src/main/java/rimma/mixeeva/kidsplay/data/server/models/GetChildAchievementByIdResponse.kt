package rimma.mixeeva.kidsplay.data.server.models

import kotlinx.serialization.Serializable

@Serializable
data class GetChildAchievementByIdResponse(
    val username: String,
    val title: String,
    val condition: String,
    val description: String,
    val obtained: Boolean
)