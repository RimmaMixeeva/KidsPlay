package rimma.mixeeva.kidsplay.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementsDao {
    @Query("SELECT * FROM Achievements")
    fun getAll(): Flow<List<AchievementsDB>>

    @Insert
    suspend fun insertAll(vararg achievementsDB: AchievementsDB)

    @Update
    suspend fun updateAll(vararg achievementsDB: AchievementsDB)
}