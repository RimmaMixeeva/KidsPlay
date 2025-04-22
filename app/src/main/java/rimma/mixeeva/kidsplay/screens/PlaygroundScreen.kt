package rimma.mixeeva.kidsplay.screens

import android.app.Activity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
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
    val enabled by remember { mutableStateOf(false) }
    val value by animateFloatAsState(
        targetValue = if (enabled) 1f else 0f,
        animationSpec = tween(durationMillis = 300)
    )

    Box {
        Image(
            painter = painterResource(id = R.drawable.gameroom),
            contentDescription = "blue wall",
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
                    .background(Color.White)
                    .clickable { viewModel.navigator.navigate(Screen.ColorGameScreen) }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.color),
                    contentDescription = "Игра в цвета",
                    modifier = Modifier.fillMaxWidth(),
                )

            }
        }
        if (viewModel.kidsAccountBitmap.value != null) {
            Image(
                bitmap = viewModel.kidsAccountBitmap.value!!.asImageBitmap(),
                contentDescription = "Скриншот экрана",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(viewModel.kidsAccountBitmap.value!!.width.toFloat() / viewModel.kidsAccountBitmap.value!!.height.toFloat())
            )
        }
    }
}