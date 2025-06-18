package rimma.mixeeva.kidsplay

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import rimma.mixeeva.kidsplay.data.database.dao.ColorGameDescriptionDao
import rimma.mixeeva.kidsplay.data.database.dao.ColorGameLevelDao
import rimma.mixeeva.kidsplay.data.database.dao.PairGameDescriptionDao
import rimma.mixeeva.kidsplay.data.database.dao.PairGameLevelDao
import rimma.mixeeva.kidsplay.navigation.Navigator
import javax.inject.Inject
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import rimma.mixeeva.kidsplay.data.database.dao.GiftDao
import rimma.mixeeva.kidsplay.navigation.Screen
import rimma.mixeeva.kidsplay.ui.theme.Orange
import rimma.mixeeva.kidsplay.ui.theme.Purple40

@HiltViewModel
class PairGameViewModel @Inject constructor(
    val navigator: Navigator,
    val pairGameLevelDao: PairGameLevelDao,
    val pairGameDescriptionDao: PairGameDescriptionDao,
    val mediaPlayer: KidsMediaPlayer
) : ViewModel() {
    val LAST_LEVEL = 30
    var pairGameLevels = pairGameLevelDao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    var pairGameDescriptions = pairGameDescriptionDao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    val allPictures = arrayListOf(
        R.drawable.banana,
        R.drawable.cucumber,
        R.drawable.watermellon,
        R.drawable.apple,
        R.drawable.pear,
        R.drawable.strawberry
    )

    //обновляемое для каждого уровня
    var gNumberOfSubLevels = mutableIntStateOf(0)
    var cardsAreOpened = mutableStateOf(true)

    //обновляемое для каждого подуровня
    var cardsForSubLevel = SnapshotStateList<PairGameItem>()
    var gCurrentSubLevelsCompleted = mutableIntStateOf(0)


    fun startLevel(levelNumber: Int) {
        Log.d("TEST6", "inside start level $levelNumber")
        pairGameDescriptions.value.map {
            Log.d("TEST6", "hrr - 4 ${pairGameDescriptions.value.first { it.id == levelNumber }}")
        }
        Log.d("TEST6", "${pairGameDescriptions.value.size} - pairGameDescriptions.value.size")
        gNumberOfSubLevels.intValue = pairGameDescriptions.value.firstOrNull { it.id == levelNumber}?.subLevels?:0
        Log.d("TEST6", "${gNumberOfSubLevels.intValue} - gNumberOfSubLevels")
        cardsAreOpened.value = pairGameDescriptions.value.firstOrNull { it.id == levelNumber}?.cardsAreOpened?:true
        Log.d("TEST6", "${cardsAreOpened.value} - cardsAreOpened.value")
        updatePictures(pairGameDescriptions.value.firstOrNull { it.id == levelNumber}?.numOfCards?:0)
        Log.d("TEST6", "${pairGameDescriptions.value.firstOrNull { it.id == levelNumber}?.numOfCards?:-1} - update pictures")
        navigator.navigate(Screen.PairGameLevelScreen(levelNumber))
    }

    fun calculateNumberOfRows(levelNumber: Int): Int {
        when (pairGameDescriptions.value.firstOrNull { it.id == levelNumber}?.numOfCards?:0){
            4 -> return 2
            6 -> return 3
            8 -> return 4
            12 -> return 4
        }
        return 0
    }

    fun updatePictures(numberOfCards: Int) {
        cardsForSubLevel.clear()
        val chosenPictures = allPictures.shuffled().subList(0, (numberOfCards / 2))
        Log.d("TEST5", numberOfCards.toString() + "num of cards")
        Log.d("TEST5", chosenPictures.size.toString() + "num of pictures")
        chosenPictures.forEach { item ->
            cardsForSubLevel.add(
                PairGameItem(
                    image = mutableIntStateOf(item),
                    wasGuessed = mutableStateOf(false),
                    opened = mutableStateOf(cardsAreOpened.value)
                )
            )
            cardsForSubLevel.add(
                PairGameItem(
                    image = mutableIntStateOf(item),
                    wasGuessed = mutableStateOf(false),
                    opened = mutableStateOf(cardsAreOpened.value)
                )
            )
        }
        cardsForSubLevel.shuffle()
    }

    suspend fun subLevelCompleted(levelNumber: Int){
        if (gCurrentSubLevelsCompleted.intValue + 1 == gNumberOfSubLevels.intValue) {
            withContext(Dispatchers.Main) {
                navigator.popBackStack()
            }
            finishLevel(levelNumber)
            //завершаем уровень игры, выдаём награды
        } else {
            //переходим на следующий подуровень
            gCurrentSubLevelsCompleted.intValue += 1
            updatePictures(pairGameDescriptions.value.firstOrNull { it.id == levelNumber}?.numOfCards?:0)
        }
    }

    suspend fun finishLevel(levelNumber: Int){
        mediaPlayer.playShortSongAndRelease(R.raw.level_completed)
        gNumberOfSubLevels.intValue = 0
        cardsForSubLevel.clear()
        gCurrentSubLevelsCompleted.intValue = 0

        if (levelNumber != LAST_LEVEL) {
            pairGameLevelDao.updateAll(pairGameLevels.value.first { it.levelNumber == (levelNumber + 1) }
                .copy(isLevelOpened = true))
        }

    }
}

data class PairGameItem(
    var image: MutableState<Int>,
    var wasGuessed: MutableState<Boolean>,
    var opened: MutableState<Boolean>
)