package rimma.mixeeva.kidsplay.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rimma.mixeeva.kidsplay.MainViewModel
import rimma.mixeeva.kidsplay.R
import rimma.mixeeva.kidsplay.navigation.Screen
import rimma.mixeeva.kidsplay.screens.components.AutoResizedText

@Composable
fun GiftScreen(viewModel: MainViewModel) {
    val gifts by viewModel.gifts.collectAsState()
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
                .padding(vertical = 60.dp)
        ) {
            AutoResizedText(
                modifier = Modifier.fillMaxWidth(),
                text = "Награды",
                size = 100.sp,
                color = Color.White
            )
            gifts.filter { item -> item.opened && !item.used}.forEach {item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                        viewModel.navigator.navigate(
                            Screen.GiftInfoScreen(
                                id = item.id,
                            )
                        )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = if (item.executor == null) R.drawable.red_ribbon else R.drawable.gold_ribbon),
                        contentDescription = "ribbon"
                    )
                    AutoResizedText(
                        text = item.title + item.id,
                        modifier = Modifier
                            .fillMaxWidth(0.9f),
                        size = 30.sp,
                        color = Color.White,
                        hasShadow = true,
                        align = TextAlign.Center
                    )
                }
            }
        }

    }
}