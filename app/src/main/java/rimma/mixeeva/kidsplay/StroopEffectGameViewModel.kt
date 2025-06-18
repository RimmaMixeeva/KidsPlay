package rimma.mixeeva.kidsplay

import TextVoicer
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.isNotNull
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import rimma.mixeeva.kidsplay.data.database.dao.ColorGameDescriptionDao
import rimma.mixeeva.kidsplay.data.database.dao.ColorGameLevelDao
import rimma.mixeeva.kidsplay.data.database.dao.GiftDao
import rimma.mixeeva.kidsplay.data.database.dao.StroopEffectGameDescriptionDao
import rimma.mixeeva.kidsplay.data.database.dao.StroopEffectGameLevelDao
import rimma.mixeeva.kidsplay.navigation.Navigator
import rimma.mixeeva.kidsplay.navigation.Screen
import rimma.mixeeva.kidsplay.ui.theme.Orange
import rimma.mixeeva.kidsplay.ui.theme.Purple40
import javax.inject.Inject

@HiltViewModel
class StroopEffectGameViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    val navigator: Navigator,
    val stroopEffectGameLevelDao: StroopEffectGameLevelDao,
    val stroopEffectGameDescriptionDao: StroopEffectGameDescriptionDao,
    var mediaPlayer: KidsMediaPlayer
) : ViewModel() {
    val LAST_LEVEL = 60

    val colorList = listOf(
        Pair("синий", Color.Blue),
        Pair("красный", Color.Red),
        Pair("зелёный", Color.Green),
        Pair("жёлтый", Color.Yellow),
        Pair("оранжевый", Orange),
        Pair("фиолетовый", Purple40),
        Pair("розовый", Color.Magenta),
        Pair("белый", Color.White),
        Pair("чёрный", Color.Black),
    )

    var stroopEffectGameLevels = stroopEffectGameLevelDao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    var stroopEffectGameDescriptions = stroopEffectGameDescriptionDao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    //обновляемое для каждого уровня
    var gNumberOfSubLevels = mutableIntStateOf(0)
    var gNumberOfWords = mutableIntStateOf(0)
    var mainPhrase = mutableStateOf("")
    var timerTime: MutableState<Int> = mutableStateOf(0)

    //обновляем для каждого подуровня
    var gCurrentSubLevelsCompleted = mutableStateOf(0)
    var uiWordsList = SnapshotStateList<Word>()


    fun startLevel(levelNumber: Int) {
        gNumberOfSubLevels.intValue =
            stroopEffectGameDescriptions.value.firstOrNull { it.id == levelNumber }?.sublevels ?: 0
        gNumberOfWords.intValue =
            stroopEffectGameDescriptions.value.firstOrNull { it.id == levelNumber }?.numOfWords ?: 0
        timerTime.value = stroopEffectGameDescriptions.value.firstOrNull { it.id == levelNumber }?.timer ?: 0
        generateWordsToShow(levelNumber)
        navigator.navigate(Screen.StroopGameLevelScreen(levelNumber))
        when (levelNumber) {
            in 1..30 -> {
                mainPhrase.value = "Найди слово,\n в котором смысл слова\n и его цвет совпадают"
                TextVoicer.voiceText(
                    context,
                    {},
                    "Найди слово, в котором смысл и его цвет совпадают"
                )
            }

            in 31..60 -> {
                mainPhrase.value = "Найди слово,\n в котором смысл слова\n и его цвет не совпадают"
                TextVoicer.voiceText(
                    context,
                    {},
                    "Найди слово, в котором смысл и его цвет не совпадают"
                )
            }

        }
    }

    fun generateWordsToShow(levelNumber: Int) {
        uiWordsList.clear()

        when (levelNumber) {
            in 1..30 -> {
                //выбираем рандомное слово, которое пользователь должен найти, смысл которого соответсвует цвету
                //и добавляем его в список
                val randomCorrectWord = colorList.shuffled().first()
                uiWordsList.add(Word(randomCorrectWord.first, randomCorrectWord.second, true))
                //выбираем слова, чей цвет не будет соответствовать смыслу
                for (i in 0..(gNumberOfWords.intValue - 2)) {
                    val randomIncorrectWord = colorList.shuffled().first().first
                    val randomIncorrectColor =
                        colorList.filter { item -> item.first != randomIncorrectWord }.shuffled()
                            .first().second
                    uiWordsList.add(Word(randomIncorrectWord, randomIncorrectColor, false))
                    uiWordsList.shuffle()
                }

            }


            in 31..60 -> {
                //выбираем рандомное слово, которое пользователь должен найти, смысл которого не соответсвует цвету
                //и добавляем его в список
                val randomIncorrectWord = colorList.shuffled().first().first
                val randomIncorrectColor =
                    colorList.filter { item -> item.first != randomIncorrectWord }.shuffled()
                        .first().second
                uiWordsList.add(Word(randomIncorrectWord, randomIncorrectColor, true))
                //выбираем слова, чей цвет будет соответствовать смыслу
                val correctWordsList = colorList.shuffled().take(gNumberOfWords.intValue - 1)
                correctWordsList.forEach { item ->
                    uiWordsList.add(Word(item.first, item.second, false))
                }
                uiWordsList.shuffle()
            }
        }
    }

    suspend fun subLevelCompleted(levelNumber: Int) {
        if (gCurrentSubLevelsCompleted.value + 1 == gNumberOfSubLevels.intValue) {
            withContext(Dispatchers.Main) {
                navigator.popBackStack()
            }
            finishLevel(levelNumber)
            //завершаем уровень игры, выдаём награды
        } else {
            //переходим на следующий подуровень
            gCurrentSubLevelsCompleted.value += 1
            generateWordsToShow(levelNumber)
        }
    }

    suspend fun finishLevel(levelNumber: Int? = null) {
        if (levelNumber != null) {
            mediaPlayer.playShortSongAndRelease(R.raw.level_completed)
            gNumberOfSubLevels.intValue = 0
            timerTime.value = 0
            uiWordsList.clear()
            gCurrentSubLevelsCompleted.value = 0

            if (levelNumber != LAST_LEVEL) {
                stroopEffectGameLevelDao.updateAll(stroopEffectGameLevels.value.first { it.levelNumber == (levelNumber + 1) }
                    .copy(isLevelOpened = true))
            }
        } else {
            gNumberOfSubLevels.intValue = 0
            timerTime.value = 0
            uiWordsList.clear()
            gCurrentSubLevelsCompleted.value = 0
        }
    }
}
    data class Word(
        var name: String,
        var color: Color,
        var correctOne: Boolean
    )