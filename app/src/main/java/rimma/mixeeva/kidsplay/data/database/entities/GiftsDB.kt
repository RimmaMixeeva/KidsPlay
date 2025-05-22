package rimma.mixeeva.kidsplay.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Gifts")
data class GiftsDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val descriptionId: Int,

    val obtained: Boolean,
    val opened: Boolean, // награда получена и отображается на экране
    val used: Boolean,  // награда получена и использована, поэтому не отображается
)