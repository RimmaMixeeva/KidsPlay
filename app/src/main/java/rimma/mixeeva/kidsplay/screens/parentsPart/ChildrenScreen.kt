package rimma.mixeeva.kidsplay.screens.parentsPart

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rimma.mixeeva.kidsplay.ParentViewModel
import rimma.mixeeva.kidsplay.R
import rimma.mixeeva.kidsplay.navigation.Navigator
import rimma.mixeeva.kidsplay.navigation.Screen
import rimma.mixeeva.kidsplay.screens.components.AutoResizedText

@Composable
fun ChildrenScreen(viewModel: ParentViewModel) {
    LaunchedEffect(Unit) {
        viewModel.getAllParentChildren()
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.blue_field),
            contentDescription = "blue field",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 100.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AutoResizedText(
                modifier = Modifier.fillMaxWidth(),
                text = "Игроки",
                size = 100.sp,
                color = Color.White
            )
            Column(modifier = Modifier
                .fillMaxHeight(0.7f)
                .verticalScroll(rememberScrollState())) {
                viewModel.children.forEach { (username, child) ->
                    Spacer(modifier = Modifier.height(12.dp))
                    PlayerCard(child.avatarId, child.nickname, {viewModel.navigator.navigate(Screen.ChildAttributesScreen(username))})
                }
            }

            Button(
                modifier = Modifier
                    .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(8.dp)),
                onClick = {
                    viewModel.addChild()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Magenta
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                AutoResizedText(text = "Добавить подопечного", size = 24.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun PlayerCard(id: Int, name: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .clip(RoundedCornerShape(24.dp))
            .border(width = 3.dp, Color.White, shape = RoundedCornerShape(24.dp))
            .clickable { onClick.invoke() }
            .background(Color.LightGray)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id),
            contentDescription = "Выбранный аватар",
            modifier = Modifier
                .fillMaxWidth(0.2f)
                .clip(CircleShape)
                .aspectRatio(1f)
                .shadow(elevation = 8.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        AutoResizedText(text = name, size = 36.sp, color = Color.White)
    }
}