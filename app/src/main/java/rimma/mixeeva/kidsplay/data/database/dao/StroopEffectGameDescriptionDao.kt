package rimma.mixeeva.kidsplay.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import rimma.mixeeva.kidsplay.data.database.entities.StroopEffectGameDescriptionDB

@Dao
interface StroopEffectGameDescriptionDao {
    @Query("SELECT * FROM StroopEffectGameDescription")
    fun getAll(): Flow<List<StroopEffectGameDescriptionDB>>
}