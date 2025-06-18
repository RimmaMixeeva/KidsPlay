package rimma.mixeeva.kidsplay.screens.pairGame

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
import rimma.mixeeva.kidsplay.PairGameViewModel
import rimma.mixeeva.kidsplay.R
import rimma.mixeeva.kidsplay.screens.components.ColorLevel
import rimma.mixeeva.kidsplay.screens.components.PairLevel

@Composable
fun PairGameSecondLevelsScreen(viewModel: PairGameViewModel) {
    val pairLevels = viewModel.pairGameLevels.collectAsState()
    val pairLevelDescription = viewModel.pairGameDescriptions.collectAsState()
    Box(contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.sand),
            contentDescription = "sand texture",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        if (pairLevels.value.size > 29) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.8f),
                userScrollEnabled = true,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                items(15) { i ->
                    PairLevel(
                        {
                            if (pairLevels.value[i + 15].isLevelOpened) {
                                viewModel.startLevel(pairLevels.value[i + 15].levelNumber)
                            }
                        },
                        pairLevels.value[i + 15]
                    )
                }
            }
        }
    }
}