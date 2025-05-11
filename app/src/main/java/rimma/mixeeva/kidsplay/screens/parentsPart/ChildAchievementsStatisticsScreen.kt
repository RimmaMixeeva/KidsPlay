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
fun ChildAchievementsStatisticsScreen(viewModel: ParentViewModel) {
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
                text = "Статистика \nдостижений игрока",
                size = 50.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(14.dp))
            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                Column{
                    Row (modifier = Modifier.background(Purple40)) {
                        Text("Название", modifier = Modifier
                            .padding(4.dp)
                            .width(140.dp),
                            fontSize = 20.sp,
                            color = Color.White)
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            "Условие получения",
                            modifier = Modifier
                                .padding(4.dp)
                                .width(200.dp),
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            "Описание",
                            modifier = Modifier
                                .padding(4.dp)
                                .width(200.dp),
                            fontSize = 20.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                           "Получено ли?",
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
                        viewModel.currentChildAchievementsList.forEach { achievement ->
                            Row(
                                modifier = Modifier.border(width = 1.dp, color = Color.Gray)
                                    .background(Color.White)
                            ) {
                                Text(
                                    achievement.title, modifier = Modifier
                                        .padding(4.dp)
                                        .width(140.dp),
                                    fontSize = 20.sp
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    achievement.condition,
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .width(200.dp),
                                    fontSize = 20.sp
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    achievement.description,
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .width(200.dp),
                                    fontSize = 20.sp
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    if (achievement.obtained) "да" else "нет",
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