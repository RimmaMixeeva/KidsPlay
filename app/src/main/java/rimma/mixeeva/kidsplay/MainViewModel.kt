package rimma.mixeeva.kidsplay

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import rimma.mixeeva.kidsplay.data.IUserPreferencesRepository
import rimma.mixeeva.kidsplay.data.UserPreferencesKeys
import rimma.mixeeva.kidsplay.data.database.dao.AchievementsDao
import rimma.mixeeva.kidsplay.data.database.dao.GiftDao
import rimma.mixeeva.kidsplay.navigation.Navigator
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    var navigator: Navigator,
    var kidsMediaPlayer: KidsMediaPlayer,
    var userPreferencesRepository: IUserPreferencesRepository,
    var giftDao: GiftDao,
    var achievementsDao: AchievementsDao,
    var mediaPlayer: KidsMediaPlayer,
    @ApplicationContext var context: Context
) : ViewModel() {

    var currentAchievementToShow: MutableState<Int?> = mutableStateOf(null)
    suspend fun activateAchievement() {
        val newAchievements = achievements.value.firstOrNull { it.id == currentAchievementToShow.value }?.copy(obtained = true)
        if (newAchievements != null) {
            achievementsDao.updateAll(newAchievements)
        }
    }

    var chosenSex: MutableState<Sex?> = mutableStateOf(Sex.MALE)

    var avatar =
        userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_AVATAR).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    var nickname =
        userPreferencesRepository.getStringFlowValue(UserPreferencesKeys.FIELD_NICK).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    var gifts = giftDao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    var achievements = achievementsDao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    var intelligence =
        userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_INTELLIGENCE).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
    var attentiveness =
        userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_ATTENTIVENESS).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
    var reaction =
        userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_REACTION).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
    var logic =
        userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_LOGIC).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
    var coins =
        userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_COINS).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
    var experience =
        userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_EXPERIENCE).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    var maleAvatarList = listOf(
        R.drawable.male_avatar_1,
        R.drawable.male_avatar_2,
        R.drawable.male_avatar_3,
        R.drawable.male_avatar_4,
        R.drawable.male_avatar_5,
        R.drawable.male_avatar_6,
        R.drawable.male_avatar_7,
        R.drawable.male_avatar_8,
        R.drawable.male_avatar_9
    )
    var femaleAvatarList = listOf(
        R.drawable.female_avatar_1,
        R.drawable.female_avatar_2,
        R.drawable.female_avatar_3,
        R.drawable.female_avatar_4,
        R.drawable.female_avatar_5,
        R.drawable.female_avatar_6,
        R.drawable.female_avatar_7,
        R.drawable.female_avatar_8,
        R.drawable.female_avatar_9
    )

    fun playSound(sound: Int) {
        kidsMediaPlayer.playShortSongAndRelease(sound)
    }

    suspend fun saveRegistrationInformation(avatar: Int, nickname: String) {
        userPreferencesRepository.setStringValue(
            UserPreferencesKeys.FIELD_NICK, nickname
        )
        userPreferencesRepository.setIntValue(
            UserPreferencesKeys.FIELD_AVATAR, avatar
        )
        userPreferencesRepository.setIntValue(UserPreferencesKeys.FIELD_INTELLIGENCE, 0)
        userPreferencesRepository.setIntValue(UserPreferencesKeys.FIELD_REACTION, 0)
        userPreferencesRepository.setIntValue(UserPreferencesKeys.FIELD_LOGIC, 0)
        userPreferencesRepository.setIntValue(UserPreferencesKeys.FIELD_ATTENTIVENESS, 0)
        userPreferencesRepository.setIntValue(UserPreferencesKeys.FIELD_COINS, 0)
        userPreferencesRepository.setIntValue(UserPreferencesKeys.FIELD_EXPERIENCE, 0)
    }

    suspend fun wasAccountRegistered(): Boolean {
        val nick =
            userPreferencesRepository.getStringFlowValue(UserPreferencesKeys.FIELD_NICK).first()
        val avatar =
            userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_AVATAR).first()
        return nick != null && avatar != null
    }

    suspend fun giftWasUsed(id: Int) {
        giftDao.updateAll(gifts.value.first { it.id == id }.copy(used = true))
    }

    suspend fun upgradeCharacteristics(
        nIntelligence: Int, nAttentiveness: Int, nReaction: Int, nLogic: Int, nCoins: Int
    ) {
        userPreferencesRepository.setIntValue(
            UserPreferencesKeys.FIELD_INTELLIGENCE,
            (intelligence.value ?: 0) + nIntelligence
        )
        userPreferencesRepository.setIntValue(
            UserPreferencesKeys.FIELD_ATTENTIVENESS,
            (attentiveness.value ?: 0) + nAttentiveness
        )
        userPreferencesRepository.setIntValue(
            UserPreferencesKeys.FIELD_REACTION,
            (reaction.value ?: 0) + nReaction
        )
        userPreferencesRepository.setIntValue(
            UserPreferencesKeys.FIELD_LOGIC,
            (logic.value ?: 0) + nLogic
        )

        userPreferencesRepository.setIntValue(
            UserPreferencesKeys.FIELD_COINS,
            (coins.value ?: 0) + nCoins
        )
        userPreferencesRepository.setIntValue(
            UserPreferencesKeys.FIELD_EXPERIENCE,
            (experience.value ?: 0) + nIntelligence + nAttentiveness + nReaction + nLogic
        )
    }

    fun changeMusicToColorGameSong() {
        mediaPlayer.destroy()
    }

}