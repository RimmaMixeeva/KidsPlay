package rimma.mixeeva.kidsplay.screens.parentsPart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rimma.mixeeva.kidsplay.ParentViewModel
import rimma.mixeeva.kidsplay.R
import rimma.mixeeva.kidsplay.screens.components.AutoResizedText
import rimma.mixeeva.kidsplay.ui.theme.Purple40


@Composable
fun ChildColorGameLevelsStatisticsScreen(viewModel: ParentViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.blue_room),
            contentDescription = "blue room",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
        )
        Column(
            modifier = Modifier
                .fillMaxHeight(1f)
                .padding(top = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            AutoResizedText(
                modifier = Modifier.fillMaxWidth(),
                text = "Статистика \nигры \"Цвета\"",
                size = 50.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(14.dp))
            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                Column {
                    Row(modifier = Modifier.background(Purple40)) {
                        Text(
                            "Уровень", modifier = Modifier
                                .padding(4.dp)
                                .width(100.dp),
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            "Звёзды",
                            modifier = Modifier
                                .padding(4.dp)
                                .width(100.dp),
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            "Таймер",
                            modifier = Modifier
                                .padding(4.dp)
                                .width(100.dp),
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "Подуровни",
                            modifier = Modifier
                                .padding(4.dp)
                                .width(140.dp),
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "Название \nокрашено",
                            modifier = Modifier
                                .padding(4.dp)
                                .width(140.dp),
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "Озвучка",
                            modifier = Modifier
                                .padding(4.dp)
                                .width(100.dp),
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "Количество \nцветов",
                            modifier = Modifier
                                .padding(4.dp)
                                .width(120.dp),
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "Доступен",
                            modifier = Modifier
                                .padding(4.dp)
                                .width(100.dp),
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "Награда",
                            modifier = Modifier
                                .padding(4.dp)
                                .width(200.dp),
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }
                    Column(
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {
                        viewModel.currentChildColorLevelsList.forEach { level ->
                            Row(
                                modifier = Modifier
                                    .border(width = 1.dp, color = Color.Gray)
                                    .background(Color.White)
                            ) {
                                Text(
                                    level.levelNumber.toString(), modifier = Modifier
                                        .padding(4.dp)
                                        .width(100.dp),
                                    fontSize = 20.sp
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    level.starsAchieved.toString(),
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .width(100.dp),
                                    fontSize = 20.sp
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    if (level.timer == 0) "нет" else (level.timer.toString() + " сек"),
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .width(100.dp),
                                    fontSize = 20.sp
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    level.subLevels.toString(),
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .width(140.dp),
                                    fontSize = 20.sp
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    if (level.isColorPhrased) "да" else "нет",
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .width(140.dp),
                                    fontSize = 20.sp
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    if (level.hasVoiceActing) "да" else "нет",
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .width(100.dp),
                                    fontSize = 20.sp
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    level.numOfColors.toString(),
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .width(120.dp),
                                    fontSize = 20.sp
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    if (level.isLevelOpened) "да" else "нет",
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .width(100.dp),
                                    fontSize = 20.sp
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    if (level.gift == 0) "награды нет" else "награда №${level.gift}",
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .width(200.dp),
                                    fontSize = 20.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }
        }
    }
}