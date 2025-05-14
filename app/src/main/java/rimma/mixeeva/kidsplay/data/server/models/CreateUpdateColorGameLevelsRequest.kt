package rimma.mixeeva.kidsplay.data.server.models

data class CreateUpdateColorGameLevelsRequest (
    val username: String,
    val levelNumber: Int,
    val starsAchieved: Int,
    val timer: Int,
    val subLevels: Int,
    val isColorPhrased: Boolean,
    val hasVoiceActing: Boolean,
    val numOfColors: Int, var isLevelOpened: Boolean,
    val gift: Int
)