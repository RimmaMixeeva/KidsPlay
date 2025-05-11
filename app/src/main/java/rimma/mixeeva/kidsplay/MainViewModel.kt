package rimma.mixeeva.kidsplay

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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

    var wasAccountRegistered = mutableStateOf(false)

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

    fun wasAccountRegistered() {
        viewModelScope.launch {
            val nick =
                userPreferencesRepository.getStringFlowValue(UserPreferencesKeys.FIELD_NICK).first()
            Log.d("TEST", "nick - " + nick.toString())
            val avatar =
                userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_AVATAR).first()
            Log.d("TEST", "avatar - " + avatar.toString())
            wasAccountRegistered.value = (nick != null && avatar != null)
        }
    }

    suspend fun giftWasUsed(id: Int) {
        giftDao.updateAll(gifts.value.first { it.id == id }.copy(used = true))
    }

    suspend fun upgradeCharacteristics(
        nIntelligence: Int, nAttentiveness: Int, nReaction: Int, nLogic: Int, nCoins: Int
    ) {
        Log.d("INTELLIGENCE ", "key: ${UserPreferencesKeys.FIELD_INTELLIGENCE} prevValue: ${intelligence.value} plus: $nIntelligence")
        userPreferencesRepository.setIntValue(
            UserPreferencesKeys.FIELD_INTELLIGENCE,
            (userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_INTELLIGENCE).first() ?: 0) + nIntelligence
        )
        Log.d("ATTENTIVENESS ", "key: ${UserPreferencesKeys.FIELD_ATTENTIVENESS} prevValue: ${attentiveness.value} plus: $nAttentiveness")
        userPreferencesRepository.setIntValue(
            UserPreferencesKeys.FIELD_ATTENTIVENESS,
            (userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_ATTENTIVENESS).first() ?: 0) + nAttentiveness
        )
        Log.d("REACTION ", "key: ${UserPreferencesKeys.FIELD_REACTION} prevValue: ${reaction.value} plus: $nReaction")
        userPreferencesRepository.setIntValue(
            UserPreferencesKeys.FIELD_REACTION,
            (userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_REACTION).first() ?: 0) + nReaction
        )
        Log.d("LOGIC ", "key: ${UserPreferencesKeys.FIELD_LOGIC} prevValue: ${logic.value} plus: $nLogic")
        userPreferencesRepository.setIntValue(
            UserPreferencesKeys.FIELD_LOGIC,
            (userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_LOGIC).first() ?: 0) + nLogic
        )
        Log.d("COINS ", "key: ${UserPreferencesKeys.FIELD_COINS} prevValue: ${coins.value} plus: $nCoins")
        userPreferencesRepository.setIntValue(
            UserPreferencesKeys.FIELD_COINS,
            (userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_COINS).first() ?: 0) + nCoins
        )
        userPreferencesRepository.setIntValue(
            UserPreferencesKeys.FIELD_EXPERIENCE,
            (userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_EXPERIENCE).first() ?: 0) + nIntelligence + nAttentiveness + nReaction + nLogic
        )
    }

    fun changeMusicToColorGameSong() {
        mediaPlayer.destroy()
    }

}