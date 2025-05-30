package rimma.mixeeva.kidsplay.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Achievements")
data class AchievementsDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val descriptionId: Int,
    var obtained: Boolean
)
