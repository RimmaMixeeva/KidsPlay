package rimma.mixeeva.kidsplay.screens.colorGame

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import rimma.mixeeva.kidsplay.ColorGameViewModel
import rimma.mixeeva.kidsplay.R
import rimma.mixeeva.kidsplay.screens.components.ColorLevel


@Composable
fun ColorGameThirdLevelsScreen(viewModel: ColorGameViewModel) {
    val colorLevels = viewModel.colorGameLevels.collectAsState()
    Box(contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.earth_texture),
            contentDescription = "earth texture",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        if (colorLevels.value.size > 44) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.8f),
                userScrollEnabled = true,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                items(15) { i ->
                    ColorLevel(
                        {
                            if (colorLevels.value[i + 30].isLevelOpened) {
                                viewModel.startLevel(colorLevels.value[i + 30].levelNumber)
                            }
                        },
                        colorLevels.value[i + 30]
                    )
                }
            }
        }
    }
}