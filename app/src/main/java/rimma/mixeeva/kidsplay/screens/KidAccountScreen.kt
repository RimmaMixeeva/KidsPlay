package rimma.mixeeva.kidsplay.screens


import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import rimma.mixeeva.kidsplay.MainViewModel
import rimma.mixeeva.kidsplay.R
import rimma.mixeeva.kidsplay.navigation.Screen
import rimma.mixeeva.kidsplay.screens.components.AutoResizedText
import rimma.mixeeva.kidsplay.screens.components.Characteristic


@Composable
fun KidAccountScreen(viewModel: MainViewModel, ava: Int?, nick: String?) {
    var enableAnimation by remember { mutableStateOf(false) }
    val animateValue by animateFloatAsState(targetValue = if (enableAnimation) 0f else 360f,
        animationSpec = repeatable(
            iterations = 1,
            animation = tween(durationMillis = 300, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        finishedListener = {
            viewModel.navigator.navController?.navigate(Screen.PlayGroundScreen) {
                popUpTo(Screen.GreetingScreen)
            }
        })

    val nickname by nick?.let { mutableStateOf(it) } ?: viewModel.nickname.collectAsState()
    val avatar by ava?.let { mutableIntStateOf(it) } ?: viewModel.avatar.collectAsState()
    val experience by viewModel.experience.collectAsState()
    val coins by viewModel.coins.collectAsState()
    val intelligence by viewModel.intelligence.collectAsState()
    val attentiveness by viewModel.attentiveness.collectAsState()
    val logic by viewModel.logic.collectAsState()
    val reaction by viewModel.reaction.collectAsState()


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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .shadow(elevation = 8.dp, shape = CircleShape)
                        .padding(bottom = 10.dp)
                ) {
                    if (avatar != null)
                    Image(painter = painterResource(avatar!!),
                        contentDescription = "Выбранный аватар",
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .clip(CircleShape)
                            .aspectRatio(1f)
                            .shadow(elevation = 8.dp)
                            .graphicsLayer { rotationZ = animateValue },
                        contentScale = ContentScale.Crop
                    )
                }
                AutoResizedText(
                    text = nickname ?: "", size = 50.sp, color = Color.White
                )
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
                        text = (experience?:0).toString(), size = 30.sp, color = Color.White
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
                        text = (coins?:0).toString(), size = 30.sp, color = Color.White
                    )
                }

                Characteristic(intelligence ?: 0, 30, color = Color.Green, name = "Интеллект")
                Spacer(modifier = Modifier.height(10.dp))
                Characteristic(attentiveness ?: 0, 90, color = Color.Magenta, name = "Внимательность")
                Spacer(modifier = Modifier.height(10.dp))
                Characteristic(reaction ?: 0, 20, color = Color.Yellow, name = "Реакция")
                Spacer(modifier = Modifier.height(10.dp))
                Characteristic(logic ?: 0, 20, color = Color.Cyan, name = "Логика")

            }
            Spacer(modifier = Modifier.fillMaxHeight(0.2f))

            if (ava != null && nick != null) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Green,
                            contentColor = Color.White,
                            disabledContainerColor = Color.White,
                            disabledContentColor = Color.LightGray
                        ),
                        onClick = {
                            CoroutineScope(Dispatchers.Default).launch {
                                viewModel.saveRegistrationInformation(avatar!!, nickname!!)
                            }
                            enableAnimation = !enableAnimation
                        },
                        modifier = Modifier
                            .size(60.dp)
                            .border(2.dp, Color.White, CircleShape),

                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "finish",
                            tint = Color.White,
                            modifier = Modifier.fillMaxSize().padding(5.dp)
                        )
                    }
                }
            }
        }
    }
}
