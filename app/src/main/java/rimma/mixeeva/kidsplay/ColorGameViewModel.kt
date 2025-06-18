package rimma.mixeeva.kidsplay


import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import rimma.mixeeva.kidsplay.data.database.dao.ColorGameDescriptionDao
import rimma.mixeeva.kidsplay.data.database.dao.ColorGameLevelDao
import rimma.mixeeva.kidsplay.data.database.dao.GiftDao
import rimma.mixeeva.kidsplay.navigation.Navigator
import rimma.mixeeva.kidsplay.navigation.Screen
import rimma.mixeeva.kidsplay.ui.theme.Orange
import rimma.mixeeva.kidsplay.ui.theme.Purple40
import javax.inject.Inject

@HiltViewModel
class ColorGameViewModel @Inject constructor(
    @ApplicationContext var context: Context,
    val navigator: Navigator,
    val colorGameLevelDao: ColorGameLevelDao,
    val colorGameDescriptionDao: ColorGameDescriptionDao,
    var mediaPlayer: KidsMediaPlayer,
    val giftDao: GiftDao,
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

    var colorGameLevels = colorGameLevelDao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    var colorGameDescriptions = colorGameDescriptionDao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    var gifts = giftDao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    //Информациия о текущем открытом уровне
    var gNumberOfSubLevels = mutableIntStateOf(0)
    var gCurrentSubLevelsCompleted = mutableIntStateOf(0)
    var gCurrentColorToGuess: MutableState<Pair<String, Color>?> = mutableStateOf(null)
    var gColors: MutableList<Pair<String, Color>> = mutableListOf()
    var correctAnswers: MutableList<Boolean> =
        mutableListOf() //true - верно ответили, false - неверно
    var colorForPhrase: MutableState<Pair<String, Color>?> = mutableStateOf(null)
    var timerTime: MutableState<Int> = mutableStateOf(0)


    fun startLevel(id: Int) {
        Log.d("TEST5", colorGameDescriptions.value.size.toString() + " shouldnt be 0")
        val colorDescriptions = colorGameDescriptions.value.firstOrNull {it.id == id}
        Log.d("TEST5", colorDescriptions.toString() + " shouldnt be null")
        if (colorDescriptions != null){
        //заполняем массив цветов, среди которых пользователь будет искать нужное
        gColors.clear()
            Log.d("TEST5", colorDescriptions.numOfColors.toString() + " - количество цветов")
        gColors.addAll(colorList.shuffled().take(colorDescriptions.numOfColors))
            Log.d("TEST5", gColors.map { item -> item.first }.toString() + " - цвета предложенные для выбора")

        //выбираем целевой цвет
        gCurrentColorToGuess.value = gColors.shuffled().take(1).first()
        Log.d("TEST5", gCurrentColorToGuess.value.toString() + " - цвет для угадывания")
       Log.d("TEST5", gColors.size.toString() + " - gcolors size")
        if (colorDescriptions.isColorPhrased) {
            val lst =  gColors.filter { it.first != gCurrentColorToGuess.value?.first }.shuffled()
            Log.d("TEST5", lst.toString() + " - перемешанные цвета кроме целевого")
            Log.d("TEST5", gColors.size.toString() + " - gcolors size")
            colorForPhrase.value = lst.take(1).first()
            Log.d("TEST5", colorForPhrase.toString() + " - цвет фразы")
            Log.d("TEST5", gColors.size.toString() + " - gcolors size")
        }
        timerTime.value = colorDescriptions.timer
        gNumberOfSubLevels.intValue = colorDescriptions.subLevels
        gCurrentSubLevelsCompleted.intValue = 0
        correctAnswers.clear()
            Log.d("TEST5", id.toString() + " - level id")
        navigator.navigate(Screen.ColorLevelScreen(id))
            Log.d("TEST5", gColors.size.toString() + " - gcolors size")
        }
    }

    suspend fun subLevelCompleted(levelNumber: Int, guessedCorrectly: Boolean, activateAchievement: (Int) -> Unit) {
        if (guessedCorrectly) {
            mediaPlayer.playShortSongAndRelease(R.raw.correct_answer)
        } else {
            mediaPlayer.playShortSongAndRelease(R.raw.button_tap_sound)
        }
        Log.d("TEST5", "LEVEL NUMBER $levelNumber")
        val colorDescriptions = colorGameDescriptions.value.first {it.id == levelNumber}
        Log.d("TEST5", "colorDescriptions $colorDescriptions")
        //меняем цвет текста
        if (colorDescriptions.isColorPhrased) {
            colorForPhrase.value =
                gColors.filter { it.first != gCurrentColorToGuess.value?.first }.shuffled().take(1)
                    .first()
        }
        correctAnswers.add(guessedCorrectly)
        if (gCurrentSubLevelsCompleted.intValue + 1 == gNumberOfSubLevels.intValue) {
            withContext(Dispatchers.Main) {
                navigator.popBackStack()
            }
            finishLevel(levelNumber, activateAchievement)
            //завершаем уровень игры, выдаём награды
        } else {
            //переходим на следующий подуровень
            gCurrentSubLevelsCompleted.intValue += 1
            //заполняем массив цветов, среди которых пользователь будет искать нужное
            gColors.clear()
            gColors.addAll(colorList.shuffled().take(colorDescriptions.numOfColors))
            //выбираем целевой цвет
            gCurrentColorToGuess.value = gColors.shuffled().take(1).first()
        }
    }

    suspend fun finishLevel(levelNumber: Int? = null, activateAchievement: suspend (Int) -> Unit) {
        if (levelNumber != null) { //успешно завершили уровень
            val bdColorLevel = colorGameLevels.value.first { it.levelNumber == levelNumber }
            val numOfAllStars = 3

            val oldStarsAchieved = bdColorLevel.starsAchieved
            val newStarsAchieved =
                ((correctAnswers.filter { it }.size) * numOfAllStars) / gNumberOfSubLevels.intValue

            if (newStarsAchieved > oldStarsAchieved) { //проверяем получили ли мы больше звезд, чтоб не сбросить хорошие результаты.
                colorGameLevelDao.updateAll(bdColorLevel.copy(starsAchieved = newStarsAchieved))
                if (newStarsAchieved == 3) { //если получили три звезды, то также необходимо выдать награду  за уровень, если она полагается
                    val giftId = colorGameDescriptions.value.firstOrNull {it.id == levelNumber}?.gift
                    if (giftId != null){
                    giftDao.updateAll(gifts.value.first { it.id == giftId }.copy(opened = true))
                        withContext(Dispatchers.Main) {
                            navigator.navigate(Screen.YouReceivedGiftScreen)
                        }
                    }

                }
            }
            //выдаём ачивку за 1й, 10й, 31й уровень
            Log.d("TEST5", " HERE 1")
            when (levelNumber){
                1 ->  activateAchievement(2)
                10 -> activateAchievement(3)
                46 -> activateAchievement(4)
            }
            if (colorGameLevels.value.filter { it.starsAchieved == 3 }.size == 60){
                delay(300)
                activateAchievement(5)
            }

            if (levelNumber != LAST_LEVEL && newStarsAchieved > 0) {
                colorGameLevelDao.updateAll(colorGameLevels.value.first { it.levelNumber == (levelNumber + 1) }
                    .copy(isLevelOpened = true))
            }
        }
        timerTime.value = 0
        gColors.clear()
        correctAnswers.clear()
        gCurrentColorToGuess.value = null
        gCurrentSubLevelsCompleted.intValue = 0
        gNumberOfSubLevels.intValue = 0
        colorForPhrase.value = null
    }
}
