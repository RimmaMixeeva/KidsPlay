package rimma.mixeeva.kidsplay.screens.parentsPart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ParentInputField(value: MutableState<String>, text: String, valid: Boolean = true, isPassword: Boolean = false){
    BasicTextField(
        value = value.value,
        onValueChange = { newValue ->
            value.value = newValue
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(horizontal = 25.dp),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        ),
        singleLine = true,
        decorationBox = { innerTextField ->
            Box(contentAlignment = Alignment.Center, modifier = Modifier.background(
                Color.White).border(width = if (valid) (-1).dp else 2.dp, color = Color.Red)) {
                if (value.value.isEmpty()) {
                    Text(
                        text,
                        fontSize = 24.sp,
                        color = Color.Gray.copy(alpha = 0.7f),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                innerTextField()
            }
        },
        visualTransformation =  if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
    )
}