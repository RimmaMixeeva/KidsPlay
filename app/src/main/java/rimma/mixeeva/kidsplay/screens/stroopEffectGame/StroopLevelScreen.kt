package rimma.mixeeva.kidsplay.screens.stroopEffectGame

import TextVoicer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rimma.mixeeva.kidsplay.R
import rimma.mixeeva.kidsplay.StroopEffectGameViewModel
import rimma.mixeeva.kidsplay.screens.colorGame.ProgressBar
import rimma.mixeeva.kidsplay.screens.components.AutoResizedText
import rimma.mixeeva.kidsplay.ui.theme.Orange

@Composable
fun StroopLevelScreen(viewModel: StroopEffectGameViewModel, id: Int) {
    val stroopGameDescriptions by viewModel.stroopEffectGameDescriptions.collectAsState()//нельзя удалять, иначе во viewmodel список будет empty,
    // так как список начинает заполняться только при появлении первого подписчика через collect или collectAsState()
    // таймер
    var timer by remember { mutableIntStateOf(viewModel.timerTime.value) }
    var isTimerRunning by remember { mutableStateOf(true) }
    var showMistakeScreen by remember { mutableStateOf(false) }

    // детали таймера
    LaunchedEffect(isTimerRunning) {
        if (isTimerRunning && timer != 0) {
            while (timer > 0) {
                delay(1000L) // Ждем 1 секунду
                timer--
            }
            showMistakeScreen = true
            delay(1000L)
            showMistakeScreen = false
            withContext(Dispatchers.Main) {
                viewModel.navigator.popBackStack()
            }
            viewModel.finishLevel()
            isTimerRunning = false
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Magenta.copy(alpha = 0.5f))
    ) {
        Image(
            painter = painterResource(id = R.drawable.bluewall2),
            contentDescription = "blue wall",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        if (showMistakeScreen) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red.copy(alpha = 0.5f))
            )
        }
        if (viewModel.gNumberOfWords.intValue > 0 && viewModel.uiWordsList.size > 0) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AutoResizedText(
                    modifier = Modifier.fillMaxWidth(),
                    text = viewModel.mainPhrase.value,
                    size = 70.sp,
                    color = Color.White,
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        userScrollEnabled = true,
                        verticalArrangement = Arrangement.Center,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        items(viewModel.uiWordsList.size) { index ->
                            Box(modifier = Modifier.padding(8.dp)) {
                                AutoResizedText(viewModel.uiWordsList[index].name,
                                    size = 50.sp,
                                    color = viewModel.uiWordsList[index].color,
                                    modifier = Modifier.clickable {
                                        if (viewModel.uiWordsList[index].correctOne) {
                                            viewModel.mediaPlayer.playShortSongAndRelease(R.raw.correct_answer)
                                            CoroutineScope(Dispatchers.Default).launch {
                                                viewModel.subLevelCompleted(id)
                                            }
                                        } else {
                                            viewModel.mediaPlayer.playShortSongAndRelease(R.raw.incorrect_answer)
                                        }
                                    },
                                    )
                            }
                        }
                    }
                    ProgressBar(
                        viewModel.gCurrentSubLevelsCompleted.value,
                        viewModel.gNumberOfSubLevels.intValue,
                        Color.Green
                    )
                    Spacer(Modifier.height(10.dp))
                    if (timer != 0) {
                        ProgressBar(
                            timer,
                            viewModel.timerTime.value,
                            Orange
                        )
                    }

                }
            }
         } else {
            AutoResizedText(text = "Loading...", size = 24.sp, color = Color.White)
        }
    }

}