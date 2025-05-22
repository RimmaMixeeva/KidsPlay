package rimma.mixeeva.kidsplay.screens.colorGame

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rimma.mixeeva.kidsplay.ColorGameViewModel
import rimma.mixeeva.kidsplay.R
import rimma.mixeeva.kidsplay.navigation.Screen
import rimma.mixeeva.kidsplay.screens.components.AutoResizedText
import rimma.mixeeva.kidsplay.ui.theme.DarkGreen
import rimma.mixeeva.kidsplay.ui.theme.DarkOrange
import rimma.mixeeva.kidsplay.ui.theme.DarkRed
import rimma.mixeeva.kidsplay.ui.theme.DarkYellow
import rimma.mixeeva.kidsplay.ui.theme.Orange

@Composable
fun ColorGameScreen(viewModel: ColorGameViewModel) {
    val gameLevels by viewModel.colorGameLevels.collectAsState()
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
                text = "ЦВЕТА",
                size = 100.sp,
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
                        viewModel.navigator.navigate(Screen.ColorGameFirstLevelsScreen)
                    },
                contentAlignment = Alignment.Center
            ) {
                Stars(1, DarkGreen, true)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .background(Color.Yellow)
                    .fillMaxWidth(0.7f)
                    .heightIn(min = 60.dp)
                    .align(Alignment.CenterHorizontally)
                    .border(width = 2.dp, color = DarkYellow)
                    .clickable {
                        if (gameLevels.firstOrNull { it.levelNumber == 16 }?.isLevelOpened == true) {
                            viewModel.navigator.navigate(Screen.ColorGameSecondLevelsScreen)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Stars(2, DarkYellow, (gameLevels.firstOrNull { it.levelNumber == 16 }?.isLevelOpened == true))
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .background(Orange)
                    .fillMaxWidth(0.7f)
                    .align(Alignment.CenterHorizontally)
                    .border(width = 2.dp, color = DarkOrange)
                    .clickable {
                        if (gameLevels.firstOrNull { it.levelNumber == 31 }?.isLevelOpened == true) {
                            viewModel.navigator.navigate(Screen.ColorGameThirdLevelsScreen)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Stars(3, DarkOrange, (gameLevels.firstOrNull { it.levelNumber == 31 }?.isLevelOpened == true))
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .background(Color.Red)
                    .fillMaxWidth(0.7f)
                    .align(Alignment.CenterHorizontally)
                    .border(width = 2.dp, color = DarkRed)
                    .clickable {
                        if (gameLevels.firstOrNull { it.levelNumber == 46 }?.isLevelOpened == true) {
                            viewModel.navigator.navigate(Screen.ColorGameFourthLevelsScreen)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Stars(4, DarkRed, (gameLevels.firstOrNull { it.levelNumber == 46 }?.isLevelOpened == true))
            }
        }
    }
}


@Composable
fun Stars(quantity: Int, darkColor: Color, isLevelUnlocked: Boolean) {
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