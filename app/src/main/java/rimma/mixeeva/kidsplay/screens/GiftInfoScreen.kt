package rimma.mixeeva.kidsplay.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import rimma.mixeeva.kidsplay.MainViewModel
import rimma.mixeeva.kidsplay.R
import rimma.mixeeva.kidsplay.screens.components.AutoResizedText
import rimma.mixeeva.kidsplay.ui.theme.DarkGreen

@Composable
fun GiftInfoScreen(
    viewModel: MainViewModel,
    id: Int
) {
    val gift = viewModel.gifts.collectAsState().value.first { it.id == id }
    Box(contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.treasury),
            contentDescription = "blue wall",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
        )
        Image(
            painter = painterResource(id = R.drawable.pergament2),
            contentDescription = "pergament",
            modifier = Modifier.scale(2f)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(0.55f)
                .fillMaxHeight(0.38f)
        ) {
            AutoResizedText(
                align = TextAlign.Center,
                text = "\"" + gift.title + gift.id + "\"",
                size = 30.sp,
                color = Color.Black,
                hasShadow = false,
                weight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                softWrap = true
            )
            Spacer(modifier = Modifier.height(40.dp))

            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                AutoResizedText(
                    text = "Описание:", size = 24.sp, color = DarkGreen, hasShadow = false,
                    modifier = Modifier.fillMaxWidth(), align = TextAlign.Left
                )
                AutoResizedText(
                    text = gift.description, size = 24.sp, color = Color.Black, hasShadow = false,
                    modifier = Modifier.fillMaxWidth(), align = TextAlign.Left, softWrap = true
                )
                Spacer(modifier = Modifier.height(20.dp))

                if (gift.executor != null) {
                    AutoResizedText(
                        text = "Исполнитель:",
                        size = 24.sp,
                        color = Color.Magenta,
                        hasShadow = false,
                        modifier = Modifier.fillMaxWidth(),
                        align = TextAlign.Left
                    )
                    AutoResizedText(
                        text = gift.executor, size = 24.sp, color = Color.Black, hasShadow = false,
                        modifier = Modifier.fillMaxWidth(), align = TextAlign.Left, softWrap = true
                    )
                } else {
                    Button(
                        onClick = {
                            viewModel.navigator.popBackStack()
                            CoroutineScope(Dispatchers.Default).launch {
                                viewModel.upgradeCharacteristics(
                                    nIntelligence = gift.intelligence,
                                    nAttentiveness = gift.attentiveness,
                                    nReaction = gift.reaction,
                                    nLogic = gift.logic,
                                    nCoins = gift.coins
                                )
                                viewModel.giftWasUsed(gift.id)
                            }
                        },
                        colors = ButtonColors(
                            containerColor = DarkGreen,
                            contentColor = Color.White,
                            disabledContentColor = Color.White,
                            disabledContainerColor = Color.LightGray
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(width = 2.dp, color = Color.Black, RoundedCornerShape(12.dp))
                    ) {
                        AutoResizedText(text = "Получить", size = 24.sp, color = Color.White)
                    }
                }
            }
        }
    }
}