package rimma.mixeeva.kidsplay.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rimma.mixeeva.kidsplay.MainViewModel
import rimma.mixeeva.kidsplay.R
import rimma.mixeeva.kidsplay.screens.components.AutoResizedText

@Composable
fun GiftScreen(viewModel: MainViewModel) {
    Box {
        Image(
            painter = painterResource(id = R.drawable.treasury),
            contentDescription = "blue wall",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 60.dp, horizontal = 20.dp)
        ) {
            AutoResizedText(
                modifier = Modifier.fillMaxWidth(),
                text = "Награды",
                size = 100.sp,
                color = Color.White
            )
        }

    }
}