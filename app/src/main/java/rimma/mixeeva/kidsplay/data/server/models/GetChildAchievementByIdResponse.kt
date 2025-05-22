package rimma.mixeeva.kidsplay.data.server.models

import kotlinx.serialization.Serializable

@Serializable
data class GetChildAchievementByIdResponse(
    val username: String,
    var descriptionId: Int,
    var obtained: Boolean
)