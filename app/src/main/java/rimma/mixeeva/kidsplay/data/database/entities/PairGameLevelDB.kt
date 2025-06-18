package rimma.mixeeva.kidsplay.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "PairGameLevels",
)
data class PairGameLevelDB(
    @PrimaryKey
    val levelNumber: Int,
    val descriptionId: Int,
    val isLevelOpened: Boolean,
)