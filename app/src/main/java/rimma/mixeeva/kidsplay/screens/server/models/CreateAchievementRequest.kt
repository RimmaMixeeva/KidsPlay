package rimma.mixeeva.kidsplay.screens.server.models

data class CreateAchievementRequest(
    val username: String,
    var title: String,
    var condition: String,
    var description: String,
    var obtained: Boolean
)