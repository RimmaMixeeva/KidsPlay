package rimma.mixeeva.kidsplay.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay
import rimma.mixeeva.kidsplay.MainViewModel
import rimma.mixeeva.kidsplay.R
import rimma.mixeeva.kidsplay.screens.components.AutoResizedText


@Composable
fun YouReceivedGiftScreen(viewModel: MainViewModel) {
    var showReward by remember { mutableStateOf(false) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.chest))
    val progress by animateLottieCompositionAsState(
        composition = composition
    )
    LaunchedEffect(progress) {
        if (progress == 1f) {
            showReward = true
        }
        if (progress == 0f) {
            viewModel.mediaPlayer.playSong(R.raw.sound_chest_with_treasures, isLooping = false)
        }
    }
    Box(contentAlignment = Alignment.Center, modifier = Modifier.clickable {
        if (progress == 1f) {
            viewModel.navigator.popBackStack()
        }
    }) {
        Image(
            painter = painterResource(id = R.drawable.cave),
            contentDescription = "cave",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (showReward) {
                AutoResizedText(text = "Новая награда", color = Color.Yellow, size = 50.sp)
            }
            LottieAnimation(
                composition = composition,
                modifier = Modifier.size(400.dp),
                progress = progress
            )
            if (showReward) {
                AutoResizedText(text = "Заберите её\nв разделе \"Награды\"", color = Color.Yellow, size = 50.sp)
            }
        }

    }
}
