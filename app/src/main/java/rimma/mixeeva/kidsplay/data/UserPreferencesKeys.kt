package rimma.mixeeva.kidsplay.data

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object UserPreferencesKeys {
    val FIELD_ID = stringPreferencesKey("id")
    val FIELD_NICK = stringPreferencesKey("nick")
    val FIELD_AVATAR = intPreferencesKey("avatar")
}