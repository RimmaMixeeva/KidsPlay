package rimma.mixeeva.kidsplay.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rimma.mixeeva.kidsplay.MainViewModel
import rimma.mixeeva.kidsplay.R
import rimma.mixeeva.kidsplay.Sex
import rimma.mixeeva.kidsplay.navigation.Screen
import rimma.mixeeva.kidsplay.screens.components.AutoResizedText

@Composable
fun ChooseAvatarScreen(viewModel: MainViewModel) {
    var choiceEnabled by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) { choiceEnabled = true }

    val avatar: MutableState<Int?> = remember { mutableStateOf(null) }

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(vertical = 100.dp),
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AutoResizedText(
                text = "Выбери аватар",
                size = 40.sp,
                color = Color.Black,
                hasShadow = false
            )
            Row(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
                    .shadow(
                        elevation = 8.dp,
                    )

            ) {
                IconButton(
                    onClick = {
                        viewModel.chosenSex.value = Sex.MALE
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .background(Color.Blue)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.male),
                        contentDescription = "choose",
                        tint = Color.White,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                IconButton(
                    onClick = {
                        viewModel.chosenSex.value = Sex.FEMALE
                    }, modifier = Modifier
                        .weight(1f)
                        .background(Color.Magenta)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.female),
                        contentDescription = "choose",
                        tint = Color.White,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier,
                userScrollEnabled = true
            ) {
                val list = mutableStateOf(
                    if (viewModel.chosenSex.value == Sex.MALE) viewModel.maleAvatarList else
                        viewModel.femaleAvatarList
                )

                items(list.value.size) { i ->
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .border(
                                4.dp,
                                if (avatar.value == list.value[i]) Color.Green else Color.LightGray,
                                CircleShape
                            )
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                if (choiceEnabled) {
                                    if (avatar.value == list.value[i]) {
                                        avatar.value = null
                                    } else {
                                        viewModel.playSound(R.raw.choice)
                                        avatar.value = list.value[i]
                                    }
                                }
                            }
                    ) {
                        Image(
                            painter = painterResource(id = list.value[i]),
                            contentDescription = "Описание изображения",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .aspectRatio(1f),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 50.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            IconButton(
                onClick = {
                    viewModel.navigator.navigate(Screen.ChooseNicknameScreen(avatar.value!!))
                    choiceEnabled = false
                },
                modifier = Modifier
                    .size(80.dp),
                enabled = avatar.value != null
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "next",
                    tint = if (avatar.value != null) Color.Green else Color.Gray,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}