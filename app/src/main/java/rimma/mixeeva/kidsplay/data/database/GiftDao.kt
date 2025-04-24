package rimma.mixeeva.kidsplay.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GiftDao {
    @Query("SELECT * FROM Gifts")
    fun getAll(): Flow<List<GiftsDB>>

    @Insert
    suspend fun insertAll(vararg giftsDB: GiftsDB)

    @Update
    suspend fun updateAll(vararg giftsDB: GiftsDB)
}