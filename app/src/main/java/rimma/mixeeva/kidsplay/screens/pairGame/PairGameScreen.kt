package rimma.mixeeva.kidsplay.screens.pairGame

import android.util.Log
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rimma.mixeeva.kidsplay.PairGameViewModel
import rimma.mixeeva.kidsplay.R
import rimma.mixeeva.kidsplay.navigation.Screen
import rimma.mixeeva.kidsplay.screens.colorGame.Stars
import rimma.mixeeva.kidsplay.screens.components.AutoResizedText
import rimma.mixeeva.kidsplay.ui.theme.DarkGreen
import rimma.mixeeva.kidsplay.ui.theme.DarkOrange
import rimma.mixeeva.kidsplay.ui.theme.DarkRed
import rimma.mixeeva.kidsplay.ui.theme.DarkYellow
import rimma.mixeeva.kidsplay.ui.theme.Orange

@Composable
fun PairGameScreen(viewModel: PairGameViewModel) {
    val pairLevels by viewModel.pairGameLevels.collectAsState()
    Box {
        Image(
            painter = painterResource(id = R.drawable.bluewall2),
            contentDescription = "blue wall",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 60.dp, horizontal = 20.dp)
        ) {
            AutoResizedText(
                modifier = Modifier.fillMaxWidth(),
                text = "НАЙДИ\n ПАРУ",
                size = 80.sp,
                color = Color.White
            )
            AutoResizedText(
                modifier = Modifier.fillMaxWidth(),
                size = 50.sp,
                text = "Уровни сложности",
                color = Color.White
            )
            Spacer(modifier = Modifier.height(90.dp))
            Box(
                modifier = Modifier
                    .background(Color.Green)
                    .fillMaxWidth(0.7f)
                    .align(Alignment.CenterHorizontally)
                    .border(width = 2.dp, color = DarkGreen)
                    .clickable {
                        viewModel.mediaPlayer.playShortSongAndRelease(R.raw.button_tap_sound)
                        viewModel.navigator.navigate(Screen.PairGameFirstLevelsScreen)
                    },
                contentAlignment = Alignment.TopCenter
            ) {
                Stars(DarkGreen, true)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .background(Orange)
                    .fillMaxWidth(0.7f)
                    .align(Alignment.CenterHorizontally)
                    .border(width = 2.dp, color = DarkOrange)
                    .clickable {
                        if (pairLevels.firstOrNull { it.levelNumber == 16 }?.isLevelOpened == true) {
                            viewModel.mediaPlayer.playShortSongAndRelease(R.raw.button_tap_sound)
                            viewModel.navigator.navigate(Screen.PairGameSecondLevelsScreen)
                        } else {
                            viewModel.mediaPlayer.playShortSongAndRelease(R.raw.blocked)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Stars(
                    DarkOrange,
                    (pairLevels.firstOrNull { it.levelNumber == 16 }?.isLevelOpened == true)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun Stars(darkColor: Color, isLevelUnlocked: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement =
        Arrangement.Center
    ) {
        Box() {
            Icon(
                imageVector = if (isLevelUnlocked) Icons.Default.PlayArrow else Icons.Default.Lock,
                contentDescription = "finish",
                tint = darkColor,
                modifier = Modifier.size(54.dp)
            )
            Icon(
                imageVector = if (isLevelUnlocked) Icons.Default.PlayArrow else Icons.Default.Lock,
                contentDescription = "finish",
                tint = Color.White,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}