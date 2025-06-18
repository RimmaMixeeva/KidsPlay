package rimma.mixeeva.kidsplay.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import rimma.mixeeva.kidsplay.data.database.entities.PairGameLevelDB

@Dao
interface PairGameLevelDao {
    @Query("SELECT * FROM PairGameLevels")
    fun getAll(): Flow<List<PairGameLevelDB>>

    @Insert
    suspend fun insertAll(vararg pairGameLevelDB: PairGameLevelDB)

    @Update
    suspend fun updateAll(vararg pairGameLevelDB: PairGameLevelDB)

}