package rimma.mixeeva.kidsplay.data.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "app_database"
        )
            .createFromAsset("database/app_database.db")
            .fallbackToDestructiveMigration(false)
    .build()

    @Singleton
    @Provides
    fun provideGiftsDao(database: AppDatabase) = database.giftDao()

    @Singleton
    @Provides
    fun provideAchievementsDao(database: AppDatabase) = database.achievementDao()

    @Singleton
    @Provides
    fun provideColorGameLevelDao(database: AppDatabase) = database.colorGameLevelDao()

}