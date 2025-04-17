package rimma.mixeeva.kidsplay.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun Characteristic(achieved: Int, all: Int, color: Color, name: String) {
    val progressWidth = remember(achieved, all) {
        (achieved.toFloat() / all.toFloat()).coerceIn(0f, 1f)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .background(Color.White)
            .border(
                width = 2.dp, color = Color.LightGray
            )
            .padding(horizontal = 2.dp, vertical = 2.dp)
            .height(34.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progressWidth)
                .background(color = color)
                .fillMaxHeight()
        )
        Row(horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize().padding(horizontal = 5.dp)) {
            Text(text = name, fontSize = 22.sp)
        }
    }

}