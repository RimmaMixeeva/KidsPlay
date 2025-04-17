package rimma.mixeeva.kidsplay.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rimma.mixeeva.kidsplay.MainViewModel
import rimma.mixeeva.kidsplay.R
import rimma.mixeeva.kidsplay.screens.components.AutoResizedText
import rimma.mixeeva.kidsplay.screens.components.Characteristic


@Composable
fun KidAccountScreen(viewModel: MainViewModel) {

    Box {
        Image(
            painter = painterResource(id = R.drawable.sea),
            contentDescription = "kids account wallper",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 100.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                AutoResizedText(
                    text = "ID2344325",
                    size = 20.sp,
                    color = Color.Black,
                    hasShadow = false
                )
            }
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .shadow(elevation = 8.dp, shape = CircleShape)
                    .clickable {
                    }
            ) {
                Image(
                    painter = painterResource(viewModel.chosenAvatar.value!!),
                    contentDescription = "Выбранный аватар",
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .clip(CircleShape)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
            }
            AutoResizedText(
                text = viewModel.chosenNickname.value,
                size = 50.sp,
                color = Color.White
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(60.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.star),
                        contentDescription = "опыт",
                        modifier = Modifier
                            .width(60.dp)
                            .aspectRatio(1f)
                            .clip(CircleShape)
                            .scale(2f)
                    )

                    AutoResizedText(
                        text = "200",
                        size = 30.sp,
                        color = Color.White
                    )
                    Image(
                        painter = painterResource(R.drawable.coin),
                        contentDescription = "деньги",
                        modifier = Modifier
                            .width(70.dp)
                            .aspectRatio(1f)
                            .clip(CircleShape)
                            .scale(1f)
                    )

                    AutoResizedText(
                        text = "33",
                        size = 30.sp,
                        color = Color.White
                    )
                }

                Characteristic(10, 30, color = Color.Green, name = "Интеллект")
                Spacer(modifier = Modifier.height(10.dp))
                Characteristic(9, 90, color = Color.Magenta, name = "Внимательность")
                Spacer(modifier = Modifier.height(10.dp))
                Characteristic(15, 20, color = Color.Yellow, name = "Реакция")
                Spacer(modifier = Modifier.height(10.dp))
                Characteristic(15, 20, color = Color.Cyan, name = "Логика")

            }
        }
    }
}
