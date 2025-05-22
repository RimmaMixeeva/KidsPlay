package rimma.mixeeva.kidsplay.data.server.models

import kotlinx.serialization.Serializable

data class CreateUpdateAchievementRequest(
    val username: String,
    var descriptionId: Int,
    var obtained: Boolean
)
