package rimma.mixeeva.kidsplay.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import rimma.mixeeva.kidsplay.R


@Composable
fun AutoResizedText(
    text: String,
    size: TextUnit,
    modifier: Modifier = Modifier,
    color: Color,
    hasShadow: Boolean = true,
    weight: FontWeight = FontWeight.Normal,
    align: TextAlign = TextAlign.Unspecified,
    softWrap: Boolean = false

) {
    var resizedTextStyle by remember {
        mutableStateOf(
            TextStyle(
                color = color,
                fontSize = size,
                fontFamily = FontFamily(Font(R.font.neucha)),
                letterSpacing = 2.sp,
                shadow = if (hasShadow) Shadow(
                    color = Color.Black,
                    blurRadius = 4f,
                    offset = Offset(8f, 8f)
                ) else null,
                textAlign = TextAlign.Center
            )
        )
    }
    Text(
        textAlign = align,
        text = text,
        modifier = modifier,
        softWrap = softWrap,
        onTextLayout = { result ->
            if (result.hasVisualOverflow) {
                resizedTextStyle = resizedTextStyle.copy(
                    fontSize = resizedTextStyle.fontSize * 0.95
                )}
        },
        style = resizedTextStyle,
        fontWeight = weight
    )
}
