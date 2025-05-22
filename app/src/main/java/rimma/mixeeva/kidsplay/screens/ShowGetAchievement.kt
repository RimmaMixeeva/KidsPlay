package rimma.mixeeva.kidsplay.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import rimma.mixeeva.kidsplay.MainViewModel
import rimma.mixeeva.kidsplay.R
import rimma.mixeeva.kidsplay.screens.components.AutoResizedText

@Composable
fun ShowGetAchievement(
    viewModel: MainViewModel
) { //всплывающая ачивка
    val achievements by viewModel.achievements.collectAsState()
    val achievementDescription by viewModel.achievementsDescription.collectAsState()
    var allowShowing by remember { mutableStateOf(false)}
    LaunchedEffect (viewModel.currentAchievementToShow.value,
        achievements.size >= (viewModel.currentAchievementToShow.value ?: Int.MAX_VALUE)
    ) {
        if (viewModel.currentAchievementToShow.value != null){
            if (achievements.firstOrNull{it.id == viewModel.currentAchievementToShow.value}?.obtained == false){
                allowShowing = true
                viewModel.activateAchievement()
                viewModel.mediaPlayer.playSong(R.raw.achievement, false)
                delay(2000)
                allowShowing = false
            }
            viewModel.currentAchievementToShow.value = null
        }
    }
    if (allowShowing && achievements.size >= viewModel.currentAchievementToShow.value!! ) {
        Box(
            modifier = Modifier
                .background(
                    Color.White
                )
                .border(color = Color.Black, width = 2.dp)
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            AutoResizedText(
                text = achievementDescription.firstOrNull { it.id == viewModel.currentAchievementToShow.value }?.title ?:"",
                size = 40.sp,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.Center),
                hasShadow = false
            )
        }
    }
}