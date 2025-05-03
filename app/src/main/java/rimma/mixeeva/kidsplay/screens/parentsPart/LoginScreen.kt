package rimma.mixeeva.kidsplay.screens.parentsPart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rimma.mixeeva.kidsplay.AUTH_STATE
import rimma.mixeeva.kidsplay.ParentViewModel
import rimma.mixeeva.kidsplay.navigation.Screen
import rimma.mixeeva.kidsplay.screens.components.AutoResizedText
import rimma.mixeeva.kidsplay.ui.theme.DarkBlue

@Composable
fun LoginScreen(viewModel: ParentViewModel) {
    val login = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 100.dp, horizontal = 20.dp)
                .fillMaxHeight()
        ) {
            AutoResizedText(
                modifier = Modifier.fillMaxWidth(),
                text = "Вход",
                size = 100.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(60.dp))

            AutoResizedText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                text = "Логин",
                size = 30.sp,
                color = Color.White,
                align = TextAlign.Start
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                ParentInputField(login, "Логин", valid = login.value.isNotEmpty())
            }
            Spacer(modifier = Modifier.height(30.dp))
            AutoResizedText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                text = "Пароль",
                size = 30.sp,
                color = Color.White,
                align = TextAlign.Start
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                ParentInputField(password, "Пароль", valid = password.value.isNotEmpty())
            }
            Spacer(modifier = Modifier.height(60.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(
                    shape = RoundedCornerShape(24.dp),
                    onClick = {
                        viewModel.login(login.value, password.value)
                    },
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(24.dp)
                        )
                        .fillMaxWidth(0.5f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkBlue.copy(alpha = 0.5f),
                        disabledContainerColor = Color.LightGray,
                    ),
                    enabled = login.value.isNotEmpty() && password.value.isNotEmpty() && viewModel.authState.value != AUTH_STATE.LOADING
                ) {
                    if (viewModel.authState.value == AUTH_STATE.LOADING) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    } else {
                        AutoResizedText(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 25.dp),
                            text = "Войти",
                            size = 30.sp,
                            color = Color.White,
                            align = TextAlign.Center
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (viewModel.authState.value == AUTH_STATE.ERROR)
                AutoResizedText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp),
                    text = "Проверьте подключение\nк интернету и корректность\nвведённых данных",
                    size = 30.sp,
                    color = Color.Red,
                    align = TextAlign.Start,
                    hasShadow = false
                )
            Spacer(modifier = Modifier.weight(1f))
            Box(modifier = Modifier
                .clickable {
                    viewModel.authState.value = AUTH_STATE.NONE
                    viewModel.navigator.navigateToNextAndDeletePreviousScreen(
                        Screen.LoginScreen,
                        Screen.RegistrationScreen
                    )
                }
                .wrapContentSize()) {
                AutoResizedText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp),
                    text = "Регистрация",
                    size = 30.sp,
                    color = Color.White,
                    align = TextAlign.Start
                )
            }
        }

    }
}
