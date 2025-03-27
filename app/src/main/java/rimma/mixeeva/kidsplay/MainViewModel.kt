package rimma.mixeeva.kidsplay

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import rimma.mixeeva.kidsplay.navigation.Navigator
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    var navigator: Navigator,
    var kidsMediaPlayer: KidsMediaPlayer
) : ViewModel() {

    var chosenAvatar: MutableState<Int?> = mutableStateOf(null)
    var chosenSex: MutableState<Sex?> = mutableStateOf(Sex.MALE)
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

    fun playSound(sound: Int){
        kidsMediaPlayer.playShortSongAndRelease(sound)
    }
}