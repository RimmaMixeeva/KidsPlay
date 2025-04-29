package rimma.mixeeva.kidsplay.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Lock
import androidx.compose.material.icons.sharp.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rimma.mixeeva.kidsplay.R
import rimma.mixeeva.kidsplay.data.database.entities.ColorGameLevelDB
import rimma.mixeeva.kidsplay.ui.theme.ButtonBlue


@Composable
fun ColorLevel(onClick: () -> Unit, level: ColorGameLevelDB) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .clickable {
                 //   if (level.isLevelOpened) {
                        onClick.invoke() //todo вернуть потом
                 //   }
                }
                .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.bluebutton),
                contentDescription = "blue button",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(60.dp),
            )
            if (level.isLevelOpened) {
                Text(text = level.levelNumber.toString(), fontSize = 30.sp, color = Color.White)
            } else {
                Icon(
                    imageVector = Icons.Sharp.Lock,
                    contentDescription = "lock",
                    modifier = Modifier.scale(1.4f),
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            Box {
                Icon(
                    imageVector = Icons.Sharp.Star,
                    contentDescription = "star",
                    modifier = Modifier.scale(1.4f),
                    tint = Color.White
                )
                Icon(
                    imageVector = Icons.Sharp.Star,
                    contentDescription = "star",
                    tint = if (level.starsAchieved > 0) Color.White else ButtonBlue
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Box {
                Icon(
                    imageVector = Icons.Sharp.Star,
                    contentDescription = "star",
                    modifier = Modifier.scale(1.4f),
                    tint = Color.White
                )
                Icon(
                    imageVector = Icons.Sharp.Star,
                    contentDescription = "star",
                    tint = if (level.starsAchieved > 1) Color.White else ButtonBlue
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Box {
                Icon(
                    imageVector = Icons.Sharp.Star,
                    contentDescription = "star",
                    modifier = Modifier.scale(1.4f),
                    tint = Color.White
                )
                Icon(
                    imageVector = Icons.Sharp.Star,
                    contentDescription = "star",
                    tint = if (level.starsAchieved > 2) Color.White else ButtonBlue
                )
            }
        }
    }

}