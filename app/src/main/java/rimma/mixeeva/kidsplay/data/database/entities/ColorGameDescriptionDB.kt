package rimma.mixeeva.kidsplay.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ColorGameDescription")
data class ColorGameDescriptionDB (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val timer: Int,
    val subLevels: Int,
    val isColorPhrased: Boolean,
    val hasVoiceActing: Boolean,
    var numOfColors: Int,
    val gift: Int?
)