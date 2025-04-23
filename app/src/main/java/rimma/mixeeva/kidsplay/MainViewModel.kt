package rimma.mixeeva.kidsplay

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import rimma.mixeeva.kidsplay.data.IUserPreferencesRepository
import rimma.mixeeva.kidsplay.data.UserPreferencesKeys
import rimma.mixeeva.kidsplay.navigation.Navigator
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    var navigator: Navigator,
    var kidsMediaPlayer: KidsMediaPlayer,
    var userPreferencesRepository: IUserPreferencesRepository
) : ViewModel() {

    var chosenAvatar: MutableState<Int?> = mutableStateOf(null)
    var chosenSex: MutableState<Sex?> = mutableStateOf(Sex.MALE)
    var chosenNickname: MutableState<String> = mutableStateOf("")
    var kidsAccountBitmap: MutableState<Bitmap?> = mutableStateOf(null)


    var intelligence: MutableState<Int> = mutableIntStateOf(0)
    var attentiveness: MutableState<Int> = mutableIntStateOf(0)
    var reaction: MutableState<Int> = mutableIntStateOf(0)
    var logic: MutableState<Int> = mutableIntStateOf(0)
    var coins: MutableState<Int> = mutableIntStateOf(0)
    var experience: MutableState<Int> = mutableIntStateOf(0)

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

    suspend fun saveRegistrationInformation() {
        if (chosenAvatar.value != null) {
            userPreferencesRepository.setStringValue(
                UserPreferencesKeys.FIELD_NICK,
                chosenNickname.value
            )
            userPreferencesRepository.setIntValue(
                UserPreferencesKeys.FIELD_AVATAR,
                chosenAvatar.value!!
            )
        }
    }

    suspend fun wasAccountRegistered(): Boolean {
        val nick = userPreferencesRepository.getStringFlowValue(UserPreferencesKeys.FIELD_NICK).first()
        val avatar = userPreferencesRepository.getIntFlowValue(UserPreferencesKeys.FIELD_AVATAR).first()
        if (nick != null) chosenNickname.value = nick
        if (avatar != null) chosenAvatar.value = avatar
        return nick != null && avatar != null
    }

    fun giftWasUsed(id: Int){

    }


}