package rimma.mixeeva.kidsplay.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import rimma.mixeeva.kidsplay.data.database.entities.GiftDescriptionDB

@Dao
interface GiftDescriptionDao {
    @Query("SELECT * FROM GiftDescription")
    fun getAll(): Flow<List<GiftDescriptionDB>>
}