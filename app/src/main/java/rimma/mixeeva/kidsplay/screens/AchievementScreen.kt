package rimma.mixeeva.kidsplay.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import rimma.mixeeva.kidsplay.MainViewModel
import rimma.mixeeva.kidsplay.R
import rimma.mixeeva.kidsplay.Sex
import rimma.mixeeva.kidsplay.data.objects.ListOfAchievements
import rimma.mixeeva.kidsplay.navigation.Screen
import rimma.mixeeva.kidsplay.screens.components.AutoResizedText
import rimma.mixeeva.kidsplay.ui.theme.Pergament


@Composable
fun AchievementScreen(viewModel: MainViewModel) {
    Box {
        Image(
            painter = painterResource(id = R.drawable.bluewall),
            contentDescription = "blue wall",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 60.dp, horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AutoResizedText(
                modifier = Modifier.fillMaxWidth(),
                text = "Достижения",
                size = 100.sp,
                color = Color.White
            )

            ListOfAchievements.achievements.filter { it.obtained }.forEach { item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            viewModel.navigator.navigate(
                                Screen.AchievementInfoScreen(
                                    title = item.title,
                                    condition = item.condition,
                                    description = item.description
                                )
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.horizontal_pergament),
                        contentDescription = "blue wall",
                    )
                    AutoResizedText(
                        text = "\"${item.title}\"",
                        modifier = Modifier.fillMaxWidth(0.7f),
                        size = 100.sp,
                        color = Color.Black,
                        hasShadow = false,
                        weight = FontWeight.Bold
                    )
                }
            }


        }
    }
}