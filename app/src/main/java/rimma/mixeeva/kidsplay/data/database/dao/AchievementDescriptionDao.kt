package rimma.mixeeva.kidsplay.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import rimma.mixeeva.kidsplay.data.database.entities.AchievementDescriptionDB


@Dao
interface AchievementDescriptionDao {
    @Query("SELECT * FROM AchievementDescription")
    fun getAll(): Flow<List<AchievementDescriptionDB>>
}