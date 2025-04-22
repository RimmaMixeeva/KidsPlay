package rimma.mixeeva.kidsplay.model

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesRepository @Inject constructor(
    @ApplicationContext val context: Context
): IUserPreferencesRepository {
    private val datastoreName = "datastore"

    private val Context.dataStore by preferencesDataStore(
        name = datastoreName
    )

    override suspend fun setStringValue(key: Preferences.Key<String>, value: String){
        context.dataStore.edit { preferences ->
            preferences[key] = value


        }
    }
    override suspend fun setIntValue(key: Preferences.Key<Int>, value: Int){
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    override fun getStringFlowValue(key: Preferences.Key<String>): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[key]

        }
    }
    override fun getIntFlowValue(key: Preferences.Key<Int>): Flow<Int?> {
        return context.dataStore.data.map { preferences ->
            preferences[key]

        }
    }
}