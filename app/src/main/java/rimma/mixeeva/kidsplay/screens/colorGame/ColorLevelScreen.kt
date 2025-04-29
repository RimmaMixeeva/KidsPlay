package rimma.mixeeva.kidsplay.screens.colorGame

import TextVoicer
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rimma.mixeeva.kidsplay.ColorGameViewModel
import rimma.mixeeva.kidsplay.R
import rimma.mixeeva.kidsplay.screens.components.AutoResizedText
import rimma.mixeeva.kidsplay.ui.theme.Orange


@Composable
fun ColorLevelScreen(viewModel: ColorGameViewModel, id: Int) {
    LaunchedEffect(viewModel.gCurrentSubLevelsCompleted.intValue) {
        if (viewModel.colorGameLevels.value.first { it.levelNumber == id }.hasVoiceActing) {
            TextVoicer.voiceText(
                viewModel.context,
                {},
                viewModel.gCurrentColorToGuess.value?.first ?: ""
            )
        }
    }
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
        if (viewModel.gColors.size > 0) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AutoResizedText(
                    modifier = Modifier.fillMaxWidth(),
                    text = "ЦВЕТА",
                    size = 100.sp,
                    color = Color.White
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            AutoResizedText(
                                modifier = Modifier.fillMaxWidth(),
                                text = viewModel.gCurrentColorToGuess.value?.first ?: "",
                                size = 70.sp,
                                color = viewModel.colorForPhrase.value?.second ?: Color.White
                            )

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier,
                                userScrollEnabled = true,
                                verticalArrangement = Arrangement.Center
                            ) {
                                items(viewModel.gColors.size) { i ->
                                    Box(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .background(if (viewModel.gColors.size > i) viewModel.gColors[i].second else Color.White)
                                            .height(60.dp)
                                            .border(width = 6.dp, color = Color.White)
                                            .shadow(elevation = 4.dp)
                                            .clickable {
                                                if (!showMistakeScreen){
                                                CoroutineScope(Dispatchers.Default).launch {
                                                    viewModel.subLevelCompleted(
                                                        id,
                                                        viewModel.gCurrentColorToGuess.value?.first == viewModel.gColors[i].first
                                                    )
                                                }
                                                }
                                            }
                                    ) { }
                                }
                            }
                        }
                    }

                    ProgressBar(
                        viewModel.gCurrentSubLevelsCompleted.intValue,
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

@Composable
fun ProgressBar(achieved: Int, all: Int, color: Color) {
    val progressWidth = remember(achieved, all) {
        (achieved.toFloat() / all.toFloat()).coerceIn(0f, 1f)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .background(Color.LightGray)
            .height(IntrinsicSize.Min)
            .border(width = 2.dp, color = Color.White)
            .shadow(elevation = 1.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progressWidth)
                .fillMaxHeight()
                .padding(2.dp)
                .background(color)
        )
        Text(
            "$achieved/$all",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            textAlign = TextAlign.Center,
            fontSize = 22.sp
        )

    }
}