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
    val descriptionId: Int,
    val starsAchieved: Int,
    val isLevelOpened: Boolean,
)