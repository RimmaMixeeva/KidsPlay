package rimma.mixeeva.kidsplay.data.preferences

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object UserPreferencesKeys {
    val FIELD_ID = stringPreferencesKey("id")
    val FIELD_NICK = stringPreferencesKey("nick")
    val FIELD_AVATAR = intPreferencesKey("avatar")

    val FIELD_INTELLIGENCE = intPreferencesKey("intelligence")
    val FIELD_ATTENTIVENESS = intPreferencesKey("attentiveness")
    val FIELD_REACTION = intPreferencesKey("reaction")
    val FIELD_LOGIC = intPreferencesKey("logic")

    val FIELD_COINS = intPreferencesKey("coins")
    val FIELD_EXPERIENCE = intPreferencesKey("experience")

}