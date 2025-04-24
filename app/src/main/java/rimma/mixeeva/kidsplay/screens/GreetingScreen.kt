package rimma.mixeeva.kidsplay.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rimma.mixeeva.kidsplay.MainViewModel
import rimma.mixeeva.kidsplay.R
import rimma.mixeeva.kidsplay.navigation.Screen
import rimma.mixeeva.kidsplay.screens.components.AutoResizedText
import rimma.mixeeva.kidsplay.screens.components.GreetingScreenButton
import rimma.mixeeva.kidsplay.ui.theme.DarkBlue
import rimma.mixeeva.kidsplay.ui.theme.DarkGreen
import rimma.mixeeva.kidsplay.ui.theme.DarkMagenta
import rimma.mixeeva.kidsplay.ui.theme.DarkYellow


@Composable
fun GreetingScreen(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    var wasAccountRegistered by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        wasAccountRegistered = viewModel.wasAccountRegistered()
    }
    Box {
        Image(
            painter = painterResource(id = R.drawable.main),
            contentDescription = "blue wall",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 100.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .width(70.dp)
                            .aspectRatio(1f),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "close icon",
                            tint = Color.Red,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                AutoResizedText(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Добро\nпожаловать!",
                    size = 100.sp,
                    color = Color.White
                )
            }

            Column {
                GreetingScreenButton(
                    onClick = {
                        if (wasAccountRegistered) viewModel.navigator.navigate(Screen.KidAccountScreen())
                        else viewModel.playSound(R.raw.blocked)
                    },
                    wasAccountRegistered = wasAccountRegistered,
                    mainColor = Color.Magenta,
                    darkColor = DarkMagenta,
                    painter = rememberVectorPainter(Icons.Default.AccountCircle),
                    text = "Аккаунт"
                )
                Spacer(modifier = Modifier.height(16.dp))
                GreetingScreenButton(
                    onClick = {
                        if (wasAccountRegistered) viewModel.navigator.navigate(Screen.AchievementScreen) else viewModel.playSound(
                            R.raw.blocked
                        )
                    },
                    wasAccountRegistered = wasAccountRegistered,
                    mainColor = Color.Blue,
                    darkColor = DarkBlue,
                    painter = painterResource(R.drawable.achievements),
                    text = "Достижения"
                )
                Spacer(modifier = Modifier.height(16.dp))
                GreetingScreenButton(
                    onClick = {
                        if (wasAccountRegistered) viewModel.navigator.navigate(Screen.GiftScreen) else viewModel.playSound(
                            R.raw.blocked
                        )
                    },
                    wasAccountRegistered = wasAccountRegistered,
                    mainColor = Color.Yellow,
                    darkColor = DarkYellow,
                    painter = painterResource(R.drawable.gift),
                    text = "Награды"
                )
            }

            GreetingScreenButton(
                onClick = {
                    if (wasAccountRegistered) viewModel.navigator.navigate(Screen.PlayGroundScreen)
                    else viewModel.navigator.navigate(Screen.ChooseAvatarScreen)
                },
                wasAccountRegistered = wasAccountRegistered,
                mainColor = Color.Green,
                darkColor = DarkGreen,
                painter = rememberVectorPainter(Icons.Default.PlayArrow),
                text = "Играть"
            )
        }
    }
}
