package rimma.mixeeva.kidsplay.screens.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GreetingScreenButton(
    onClick: () -> Unit,
    wasAccountRegistered: Boolean,
    mainColor: Color,
    darkColor: Color,
    painter: Painter,
    text: String
) {
    Button(
        shape = RoundedCornerShape(24.dp),
        onClick = onClick,
        modifier = Modifier
            .border(width = 2.dp, color = darkColor, shape = RoundedCornerShape(24.dp))
            .fillMaxWidth(0.7f),
        colors = ButtonDefaults.buttonColors(
            containerColor = mainColor,
            disabledContainerColor = Color.LightGray,
        )
    ) {
        if (wasAccountRegistered || mainColor == Color.Green) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    painter = painter,
                    contentDescription = text,
                    tint = darkColor,
                    modifier = Modifier
                        .size(55.dp)
                )
                Icon(
                    painter = painter,
                    contentDescription = text,
                    tint = Color.White,
                    modifier = Modifier
                        .size(50.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            AutoResizedText(text = text, size = 36.sp, color = Color.White)
        } else {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    painter = rememberVectorPainter(Icons.Default.Lock),
                    contentDescription = "заблокировано",
                    tint = darkColor,
                    modifier = Modifier.size(55.dp)
                )
            }
        }
    }
}