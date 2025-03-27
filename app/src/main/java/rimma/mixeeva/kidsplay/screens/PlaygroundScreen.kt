package rimma.mixeeva.kidsplay.screens

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rimma.mixeeva.kidsplay.R
import rimma.mixeeva.kidsplay.screens.components.AutoResizedText

@Composable
fun PlaygroundScreen(colorPlay: () -> Unit) {
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
                    .width(100.dp)
                    .padding(vertical = 50.dp)
                    .aspectRatio(1f)
                    .background(Color.White)
                    .clip(CircleShape)
                    .clickable { colorPlay.invoke() }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.colorgame),
                    contentDescription = "Описание изображения",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )

            }
        }
    }
}