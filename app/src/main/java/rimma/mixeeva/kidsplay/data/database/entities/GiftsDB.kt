package rimma.mixeeva.kidsplay.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Gifts")
data class GiftsDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val title: String ,
    val condition: String,
    val description: String,

    val obtained: Boolean, //награда получена, но не отображается
    val opened: Boolean, // награда получена и отображается на экране
    val used: Boolean,  // награда получена и использована, поэтому не отображается

    val executor: String?,

    val intelligence: Int,
    val attentiveness: Int,
    val reaction: Int,
    val logic: Int,

    val coins: Int
)