package rimma.mixeeva.kidsplay.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import rimma.mixeeva.kidsplay.data.database.entities.AchievementsDB

@Dao
interface AchievementsDao {
    @Query("SELECT * FROM Achievements")
    fun getAll(): Flow<List<AchievementsDB>>

    @Insert
    suspend fun insertAll(vararg achievementsDB: AchievementsDB)

    @Update
    suspend fun updateAll(vararg achievementsDB: AchievementsDB)
}