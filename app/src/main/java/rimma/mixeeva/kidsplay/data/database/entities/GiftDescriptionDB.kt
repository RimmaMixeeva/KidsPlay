package rimma.mixeeva.kidsplay.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GiftDescription")
data class GiftDescriptionDB (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String ,
    val condition: String,
    val description: String,

    val executor: String?,

    val intelligence: Int,
    val attentiveness: Int,
    val reaction: Int,
    val logic: Int,

    val coins: Int
)