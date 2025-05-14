package rimma.mixeeva.kidsplay.data.preferences

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow


interface IUserPreferencesRepository {

    suspend fun setStringValue(key: Preferences.Key<String>, value: String)
    suspend fun setIntValue(key: Preferences.Key<Int>, value: Int)

    fun getStringFlowValue(key: Preferences.Key<String>): Flow<String?>
    fun getIntFlowValue(key: Preferences.Key<Int>): Flow<Int?>

}