package rimma.mixeeva.kidsplay.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "StroopEffectGameLevels",
)
data class StroopEffectLevelDB(
    @PrimaryKey(autoGenerate = true)
    val levelNumber: Int,
    val descriptionId: Int,
    val starsAchieved: Int,
    val isLevelOpened: Boolean,
)
