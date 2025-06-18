package rimma.mixeeva.kidsplay.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import rimma.mixeeva.kidsplay.data.database.entities.StroopEffectLevelDB

@Dao
interface StroopEffectGameLevelDao {
    @Query("SELECT * FROM StroopEffectGameLevels")
    fun getAll(): Flow<List<StroopEffectLevelDB>>

    @Insert
    suspend fun insertAll(vararg stroopEffectGameLevelDB: StroopEffectLevelDB)

    @Update
    suspend fun updateAll(vararg stroopEffectGameLevelDB: StroopEffectLevelDB)

}