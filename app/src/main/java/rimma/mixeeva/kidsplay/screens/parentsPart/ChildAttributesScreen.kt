package rimma.mixeeva.kidsplay.screens.parentsPart

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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rimma.mixeeva.kidsplay.ParentViewModel
import rimma.mixeeva.kidsplay.R
import rimma.mixeeva.kidsplay.navigation.Screen
import rimma.mixeeva.kidsplay.screens.components.GreetingScreenButton
import rimma.mixeeva.kidsplay.ui.theme.DarkBlue
import rimma.mixeeva.kidsplay.ui.theme.DarkYellow

@Composable
fun ChildAttributesScreen(viewModel: ParentViewModel, username: String, avatar: Int) {
    LaunchedEffect(Unit) {
        viewModel.getChildAttributes(username)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.blue_room),
            contentDescription = "blue room",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
        )
        Column(
            modifier = Modifier
                .padding(vertical = 100.dp, horizontal = 20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .clip(
                        RoundedCornerShape(24.dp)
                    )
                    .shadow(elevation = 2.dp)
                    .border(width = 2.dp, color = Color.Gray)
                    .background(Color.White)
                    .padding(20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(modifier = Modifier, horizontalArrangement = Arrangement.Center) {
                    Image(
                        painter = painterResource(avatar),
                        contentDescription = "Выбранный аватар",
                        modifier = Modifier
                            .fillMaxWidth(0.2f)
                            .clip(CircleShape)
                            .aspectRatio(1f)
                            .shadow(elevation = 8.dp)
                            .border(width = 2.dp, color = Color.Gray, shape = CircleShape),
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                }
                Text(text = "Общая информация", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = "Имя: ${
                        viewModel.currentOpenedChildAttributes.value?.username?.substringBefore(
                            "@#@"
                        )
                    }", fontSize = 24.sp
                )
                Text(
                    text = "Интеллект: ${viewModel.currentOpenedChildAttributes.value?.intelligence ?: ""}",
                    fontSize = 24.sp
                )
                Text(
                    text = "Логика: ${viewModel.currentOpenedChildAttributes.value?.logic ?: ""}",
                    fontSize = 24.sp
                )
                Text(
                    text = "Реакция: ${viewModel.currentOpenedChildAttributes.value?.reaction ?: ""}",
                    fontSize = 24.sp
                )
                Text(
                    text = "Внимательность: ${viewModel.currentOpenedChildAttributes.value?.attentiveness ?: ""}",
                    fontSize = 24.sp
                )
                Text(
                    text = "Монеты: ${viewModel.currentOpenedChildAttributes.value?.coins ?: ""}",
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            GreetingScreenButton(
                onClick = {
                    viewModel.getChildGifts(username)
                    viewModel.navigator.navigate(Screen.ChildGiftsStatisticsScreen)

                },
                wasAccountRegistered = true,
                mainColor = Color.Yellow,
                darkColor = DarkYellow,
                painter = painterResource(R.drawable.gift),
                text = "Статистика \nнаград"
            )
            Spacer(modifier = Modifier.height(12.dp))
            GreetingScreenButton(
                onClick = {
                    viewModel.getChildAchievements(username)
                    viewModel.navigator.navigate(Screen.ChildAchievementsStatisticsScreen)
                },
                wasAccountRegistered = true,
                mainColor = Color.Blue,
                darkColor = DarkBlue,
                painter = painterResource(R.drawable.achievements),
                text = "Статистика \nдостижений"
            )
            Spacer(modifier = Modifier.height(12.dp))
            GreetingScreenButton(
                onClick = {
                    viewModel.getChildColorGameLevels(username)
                    viewModel.navigator.navigate(Screen.ChildColorGameLevelsStatisticsScreen)
                },
                wasAccountRegistered = true,
                mainColor = Color.Magenta,
                darkColor = DarkBlue,
                painter = painterResource(R.drawable.colorgame),
                text = "Статистика \nигры \"Цвета\""
            )
        }
    }
}