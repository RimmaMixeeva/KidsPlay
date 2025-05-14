package rimma.mixeeva.kidsplay.data.server.models

data class CreateUpdateAchievementRequest(
    val username: String,
    var title: String,
    var condition: String,
    var description: String,
    var obtained: Boolean
)