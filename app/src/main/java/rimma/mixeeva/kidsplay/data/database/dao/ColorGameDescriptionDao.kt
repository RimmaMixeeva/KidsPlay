package rimma.mixeeva.kidsplay.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import rimma.mixeeva.kidsplay.data.database.entities.ColorGameDescriptionDB

@Dao
interface ColorGameDescriptionDao {
    @Query("SELECT * FROM ColorGameDescription")
    fun getAll(): Flow<List<ColorGameDescriptionDB>>
}