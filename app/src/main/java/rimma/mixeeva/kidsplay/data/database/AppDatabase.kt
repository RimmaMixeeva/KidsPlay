package rimma.mixeeva.kidsplay.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GiftsDB::class, AchievementsDB::class], version = 1, exportSchema = true)
abstract class AppDatabase: RoomDatabase() {
    abstract fun giftDao(): GiftDao
    abstract fun achievementDao(): AchievementsDao
}