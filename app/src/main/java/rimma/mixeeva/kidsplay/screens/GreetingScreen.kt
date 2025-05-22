package rimma.mixeeva.kidsplay.screens

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
    val gifts by viewModel.gifts.collectAsState()//нельзя удалять, иначе во viewmodel список gifts будет empty,
    // так как список начинает заполняться только при появлении первого подписчика через collect или collectAsState()
    val giftsDescription by viewModel.giftsDescription.collectAsState()//нельзя удалять, иначе во viewmodel список gifts будет empty,
    // так как список начинает заполняться только при появлении первого подписчика через collect или collectAsState()
    LaunchedEffect(Unit) { viewModel.wasAccountRegistered() }
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
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = {
                            viewModel.navigator.navigate(Screen.LoginScreen)
                        },
                        modifier = Modifier
                            .width(70.dp)
                            .aspectRatio(1f)
                            .border(width = 2.dp, color = Color.Gray, shape = CircleShape),
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "close icon",
                            tint = Color.Gray,
                            modifier = Modifier.fillMaxSize().background(Color.White)
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
                        if (viewModel.wasAccountRegistered.value) viewModel.navigator.navigate(Screen.KidAccountScreen())
                        else viewModel.playSound(R.raw.blocked)
                    },
                    wasAccountRegistered = viewModel.wasAccountRegistered.value,
                    mainColor = Color.Magenta,
                    darkColor = DarkMagenta,
                    painter = rememberVectorPainter(Icons.Default.AccountCircle),
                    text = "Аккаунт"
                )
                Spacer(modifier = Modifier.height(16.dp))
                GreetingScreenButton(
                    onClick = {
                        if (viewModel.wasAccountRegistered.value) viewModel.navigator.navigate(Screen.AchievementScreen) else viewModel.playSound(
                            R.raw.blocked
                        )
                    },
                    wasAccountRegistered = viewModel.wasAccountRegistered.value,
                    mainColor = Color.Blue,
                    darkColor = DarkBlue,
                    painter = painterResource(R.drawable.achievements),
                    text = "Достижения"
                )
                Spacer(modifier = Modifier.height(16.dp))
                GreetingScreenButton(
                    onClick = {
                        if (viewModel.wasAccountRegistered.value) viewModel.navigator.navigate(Screen.GiftScreen) else viewModel.playSound(
                            R.raw.blocked
                        )
                    },
                    wasAccountRegistered = viewModel.wasAccountRegistered.value,
                    mainColor = Color.Yellow,
                    darkColor = DarkYellow,
                    painter = painterResource(R.drawable.gift),
                    text = "Награды"
                )
            }

            GreetingScreenButton(
                onClick = {
                    if (viewModel.wasAccountRegistered.value) viewModel.navigator.navigate(Screen.PlayGroundScreen)
                    else viewModel.navigator.navigate(Screen.ChooseAvatarScreen)
                },
                wasAccountRegistered = viewModel.wasAccountRegistered.value,
                mainColor = Color.Green,
                darkColor = DarkGreen,
                painter = rememberVectorPainter(Icons.Default.PlayArrow),
                text = "Играть"
            )
        }
    }
}
