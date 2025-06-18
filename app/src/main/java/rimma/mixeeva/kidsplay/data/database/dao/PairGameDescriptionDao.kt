package rimma.mixeeva.kidsplay.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import rimma.mixeeva.kidsplay.data.database.entities.PairGameDescriptionDB

@Dao
interface PairGameDescriptionDao {
    @Query("SELECT * FROM PairGameDescription")
    fun getAll(): Flow<List<PairGameDescriptionDB>>
}