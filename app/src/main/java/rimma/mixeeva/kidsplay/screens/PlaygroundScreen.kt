package rimma.mixeeva.kidsplay.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rimma.mixeeva.kidsplay.MainViewModel
import rimma.mixeeva.kidsplay.R
import rimma.mixeeva.kidsplay.navigation.Screen
import rimma.mixeeva.kidsplay.screens.components.AutoResizedText

@Composable
fun PlaygroundScreen(viewModel: MainViewModel) {
    Box {
        Image(
            painter = painterResource(id = R.drawable.gameroom),
            contentDescription = "game room",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 60.dp, horizontal = 20.dp)
        ) {
            AutoResizedText(
                modifier = Modifier.fillMaxWidth(),
                text = "ИГРЫ",
                size = 100.sp,
                color = Color.White
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .padding(vertical = 50.dp)
                    .aspectRatio(1f)
                    .clickable {
                        viewModel.changeMusicToColorGameSong()
                        viewModel.navigator.navigate(Screen.ColorGameScreen) }
                    .border(width = 6.dp, color = Color.White, shape = RoundedCornerShape(24.dp))
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(24.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.color),
                    contentDescription = "Игра в цвета",
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(24.dp)),
                )

            }
        }
    }
}