package rimma.mixeeva.kidsplay.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "StroopEffectGameDescription")
data class StroopEffectGameDescriptionDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val timer: Int,
    val sublevels: Int,
    val numOfWords: Int,
    val gift: Int?
)