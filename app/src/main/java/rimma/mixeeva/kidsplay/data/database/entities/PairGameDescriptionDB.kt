package rimma.mixeeva.kidsplay.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PairGameDescription")
data class PairGameDescriptionDB (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val numOfCards: Int,
    val subLevels: Int,
    val cardsAreOpened: Boolean,
    val gift: Int?
)