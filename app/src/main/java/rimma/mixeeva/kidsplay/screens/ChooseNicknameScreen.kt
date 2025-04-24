package rimma.mixeeva.kidsplay.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rimma.mixeeva.kidsplay.MainViewModel
import rimma.mixeeva.kidsplay.navigation.Screen
import rimma.mixeeva.kidsplay.screens.components.AutoResizedText


@Composable
fun ChooseNicknameScreen(viewModel: MainViewModel, avatar: Int? = null) {
    var nickname by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(vertical = 100.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            AutoResizedText(
                text = "Придумай имя",
                size = 40.sp,
                color = Color.Black,
                hasShadow = false
            )
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .border(
                        4.dp,
                        Color.LightGray,
                        CircleShape
                    )
            ) {
                if (avatar != null) {
                    Image(
                        painter = painterResource(avatar),
                        contentDescription = "Выбранный аватар",
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .clip(CircleShape)
                            .aspectRatio(1f),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .background(Color.White, CircleShape)
                    .border(2.dp, Color.LightGray, CircleShape)
            ) {
                BasicTextField(
                    value = nickname,
                    onValueChange = { newValue ->
                        nickname = newValue
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(horizontal = 25.dp),
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    ),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        Box(contentAlignment = Alignment.CenterStart) {
                            if (nickname.isEmpty()) {
                                Text(
                                    "Имя",
                                    fontSize = 24.sp,
                                    color = Color.Gray.copy(alpha = 0.7f),
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

        }

        Row(
            modifier = Modifier
                .padding(horizontal = 50.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            IconButton(
                onClick = {
                    viewModel.navigator.popBackStack()
                },
                modifier = Modifier
                    .size(80.dp),
                enabled = true
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back",
                    tint = Color.Green,
                    modifier = Modifier.fillMaxSize()
                )
            }
            IconButton(
                onClick = {
                    if (avatar != null) {
                        viewModel.navigator.navigate(Screen.KidAccountScreen(avatar, nickname))
                    }
                },
                modifier = Modifier
                    .size(80.dp),
                enabled = nickname.isNotEmpty()
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "next",
                    tint = if (nickname.isNotEmpty()) Color.Green else Color.Gray,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }


}