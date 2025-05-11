package rimma.mixeeva.kidsplay.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "ColorGameLevels",
)
data class ColorGameLevelDB(
    @PrimaryKey
    val levelNumber: Int,

    val starsAchieved: Int,
    val timer: Int,
    val subLevels: Int,
    val isColorPhrased: Boolean,
    val hasVoiceActing: Boolean,
    var numOfColors: Int,
    var isLevelOpened: Boolean,
    val gift: Int?
)