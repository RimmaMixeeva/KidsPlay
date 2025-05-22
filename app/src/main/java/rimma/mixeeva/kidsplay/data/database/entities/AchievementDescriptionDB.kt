package rimma.mixeeva.kidsplay.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AchievementDescription")
data class AchievementDescriptionDB (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var title: String,
    var condition: String,
    var description: String,
)