package rimma.mixeeva.kidsplay.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rimma.mixeeva.kidsplay.MainViewModel
import rimma.mixeeva.kidsplay.R
import rimma.mixeeva.kidsplay.screens.components.AutoResizedText
import rimma.mixeeva.kidsplay.ui.theme.DarkGreen

@Composable
fun AchievementInfoScreen(
   viewModel: MainViewModel,
   id: Int
) {
    val achievement = viewModel.achievements.collectAsState().value.first { it.id == id }
    Box(contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.bluewall),
            contentDescription = "blue wall",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
        )
        Image(
            painter = painterResource(id = R.drawable.pergament),
            contentDescription = "pergament",
            modifier = Modifier.fillMaxHeight(0.7f),
            contentScale = ContentScale.FillBounds
        )
        Column(modifier = Modifier
            .fillMaxWidth(0.6f)
            .fillMaxHeight(0.4f)) {
            AutoResizedText(
                align = TextAlign.Center,
                text = "\"" + achievement.title + "\"",
                size = 30.sp,
                color = Color.Black,
                hasShadow = false,
                weight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                softWrap = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column {
                AutoResizedText(
                    text = "Условие:", size = 24.sp, color = DarkGreen, hasShadow = false,
                    modifier = Modifier.fillMaxWidth(), align = TextAlign.Left
                )
                AutoResizedText(
                    text = achievement.condition, size = 24.sp, color = Color.Black, hasShadow = false,
                    modifier = Modifier.fillMaxWidth(), align = TextAlign.Left, softWrap = true
                )
                Spacer(modifier = Modifier.height(10.dp))
                AutoResizedText(
                    text = "Описание:", size = 24.sp, color = DarkGreen, hasShadow = false,
                    modifier = Modifier.fillMaxWidth(), align = TextAlign.Left
                )
                AutoResizedText(
                    text = achievement.description, size = 24.sp, color = Color.Black, hasShadow = false,
                    modifier = Modifier.fillMaxWidth(), align = TextAlign.Left, softWrap = true
                )

            }

        }

    }
}