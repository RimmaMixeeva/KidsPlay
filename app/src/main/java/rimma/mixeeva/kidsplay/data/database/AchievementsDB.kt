package rimma.mixeeva.kidsplay.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Achievements")
data class AchievementsDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    var title: String,
    var condition: String,
    var description: String,
    var obtained: Boolean
)
