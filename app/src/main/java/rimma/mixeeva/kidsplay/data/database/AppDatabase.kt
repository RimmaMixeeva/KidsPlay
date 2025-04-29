package rimma.mixeeva.kidsplay.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import rimma.mixeeva.kidsplay.data.database.dao.AchievementsDao
import rimma.mixeeva.kidsplay.data.database.dao.ColorGameLevelDao
import rimma.mixeeva.kidsplay.data.database.dao.GiftDao
import rimma.mixeeva.kidsplay.data.database.entities.AchievementsDB
import rimma.mixeeva.kidsplay.data.database.entities.ColorGameLevelDB
import rimma.mixeeva.kidsplay.data.database.entities.GiftsDB

@Database(entities = [GiftsDB::class, AchievementsDB::class, ColorGameLevelDB::class], version = 1, exportSchema = true)
abstract class AppDatabase: RoomDatabase() {
    abstract fun giftDao(): GiftDao
    abstract fun achievementDao(): AchievementsDao
    abstract fun colorGameLevelDao(): ColorGameLevelDao
}