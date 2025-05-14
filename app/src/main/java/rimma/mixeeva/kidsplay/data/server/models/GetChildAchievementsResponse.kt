package rimma.mixeeva.kidsplay.data.server.models

import kotlinx.serialization.Serializable

@Serializable
data class GetChildAchievementsResponse (
    var listId: List<String>
)