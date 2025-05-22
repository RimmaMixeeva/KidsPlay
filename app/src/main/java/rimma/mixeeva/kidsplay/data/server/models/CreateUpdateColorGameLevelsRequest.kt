package rimma.mixeeva.kidsplay.data.server.models

data class CreateUpdateColorGameLevelsRequest (
    val username: String,
    val descriptionId: Int,
    val levelNumber: Int,
    val starsAchieved: Int,
    var isLevelOpened: Boolean,
)