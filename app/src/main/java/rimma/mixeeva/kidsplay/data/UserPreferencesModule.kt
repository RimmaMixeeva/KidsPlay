package rimma.mixeeva.kidsplay.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserPreferencesModule {
    @Binds
    @Singleton
    abstract fun provideIUserPreferences(userPreferencesRepository: UserPreferencesRepository): IUserPreferencesRepository
}