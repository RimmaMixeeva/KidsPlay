package rimma.mixeeva.kidsplay.screens.pairGame

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
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
import kotlinx.coroutines.launch
import rimma.mixeeva.kidsplay.PairGameViewModel
import rimma.mixeeva.kidsplay.R
import rimma.mixeeva.kidsplay.screens.colorGame.ProgressBar
import rimma.mixeeva.kidsplay.screens.components.AutoResizedText
import rimma.mixeeva.kidsplay.ui.theme.PairGameBackground

@Composable
fun PairGameLevelScreen(viewModel: PairGameViewModel, levelNumber: Int) {
    var allowClick by remember { mutableStateOf(true) } //когда уже нажато на два объекта, нажатие на третье блокируется
    
    DisposableEffect(Unit) {
        onDispose {
            viewModel.gCurrentSubLevelsCompleted.intValue = 0
        }
    }

    val chosenItemIndex: MutableState<Int?> = remember { mutableStateOf(null) }
    val secondChosenItemIndex: MutableState<Int?> = remember { mutableStateOf(null) }
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.bluewall2),
                contentDescription = "pair game background",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize(),
            )
            if (viewModel.cardsForSubLevel.filter { it.wasGuessed.value }.size != viewModel.cardsForSubLevel.size){
            Column (modifier = Modifier.fillMaxHeight(0.8f), horizontalAlignment = Alignment.CenterHorizontally) {
                AutoResizedText(
                    modifier = Modifier.fillMaxWidth(),
                    text = "НАЙДИ ПАРУ",
                    size = 100.sp,
                    color = Color.White
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(viewModel.calculateNumberOfRows(levelNumber)),
                    modifier = Modifier.weight(1f),
                    userScrollEnabled = true,
                    verticalArrangement = Arrangement.Center
                ) {
                    items(viewModel.cardsForSubLevel.size) { index ->
                        if (!viewModel.cardsForSubLevel[index].wasGuessed.value) {
                            Box(
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .padding(4.dp)
                                    .shadow(elevation = 4.dp)
                                    .border(
                                        width = 3.dp,
                                        color = if (chosenItemIndex.value == index || secondChosenItemIndex.value == index) Color.Blue else Color.White
                                    )
                                    .background(if (viewModel.cardsForSubLevel[index].wasGuessed.value) Color.Transparent else PairGameBackground)
                                    .clickable {
                                        viewModel.mediaPlayer.playShortSongAndRelease(R.raw.button_tap_sound)
                                        if (!viewModel.cardsForSubLevel[index].wasGuessed.value && allowClick && chosenItemIndex.value != index) {
                                            CoroutineScope(Dispatchers.Default).launch {
                                                if (chosenItemIndex.value == null) {
                                                    chosenItemIndex.value = index
                                                    viewModel.cardsForSubLevel[index].opened.value = true
                                                } else if (chosenItemIndex.value != null) {
                                                    secondChosenItemIndex.value = index
                                                    viewModel.cardsForSubLevel[index].opened.value = true
                                                    allowClick = false
                                                    delay(600)
                                                    if (viewModel.cardsForSubLevel[chosenItemIndex.value!!].image.value == viewModel.cardsForSubLevel[index].image.value) {
                                                        viewModel.mediaPlayer.playShortSongAndRelease(R.raw.correct_answer)
                                                        viewModel.cardsForSubLevel[chosenItemIndex.value!!].wasGuessed.value =
                                                            true
                                                        viewModel.cardsForSubLevel[index].wasGuessed.value = true
                                                        if (viewModel.cardsForSubLevel.filter { it.wasGuessed.value }.size == viewModel.cardsForSubLevel.size) { //подуровень закончен
                                                            viewModel.subLevelCompleted(levelNumber)
                                                        }
                                                    } else {
                                                        viewModel.mediaPlayer.playShortSongAndRelease(R.raw.incorrect_answer)
                                                        if (!viewModel.cardsAreOpened.value){
                                                        viewModel.cardsForSubLevel[chosenItemIndex.value!!].opened.value =
                                                            false
                                                        viewModel.cardsForSubLevel[index].opened.value = false
                                                        }
                                                    }
                                                    allowClick = true
                                                    chosenItemIndex.value = null
                                                    secondChosenItemIndex.value = null
                                                }
                                            }
                                        }

                                    }
                            ) {
                                if (viewModel.cardsForSubLevel[index].opened.value) {
                                    Image(
                                        painter = painterResource(viewModel.cardsForSubLevel[index].image.value),
                                        contentDescription = "paired picture",
                                    )
                                }
                            }
                        }
                    }
                }
                ProgressBar(
                    viewModel.gCurrentSubLevelsCompleted.intValue,
                    viewModel.gNumberOfSubLevels.intValue,
                    Color.Green
                )
            }} else {
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