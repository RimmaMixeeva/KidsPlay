package rimma.mixeeva.kidsplay.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import rimma.mixeeva.kidsplay.data.database.entities.AchievementsDB
import rimma.mixeeva.kidsplay.data.database.entities.ColorGameLevelDB

@Dao
interface ColorGameLevelDao {
    @Query("SELECT * FROM ColorGameLevels")
    fun getAll(): Flow<List<ColorGameLevelDB>>

    @Insert
    suspend fun insertAll(vararg colorGameLevelDB: ColorGameLevelDB)

    @Update
    suspend fun updateAll(vararg colorGameLevelDB: ColorGameLevelDB)
}