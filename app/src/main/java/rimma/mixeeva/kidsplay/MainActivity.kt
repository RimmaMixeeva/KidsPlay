package rimma.mixeeva.kidsplay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import rimma.mixeeva.kidsplay.navigation.Navigator
import rimma.mixeeva.kidsplay.navigation.Screen
import rimma.mixeeva.kidsplay.screens.AchievementScreen
import rimma.mixeeva.kidsplay.screens.ChooseAvatarScreen
import rimma.mixeeva.kidsplay.screens.ChooseNicknameScreen
import rimma.mixeeva.kidsplay.screens.colorGame.ColorGameScreen
import rimma.mixeeva.kidsplay.screens.GiftScreen
import rimma.mixeeva.kidsplay.screens.GreetingScreen
import rimma.mixeeva.kidsplay.screens.KidAccountScreen
import rimma.mixeeva.kidsplay.screens.PlaygroundScreen
import rimma.mixeeva.kidsplay.screens.AchievementInfoScreen
import rimma.mixeeva.kidsplay.screens.colorGame.ColorLevelScreen
import rimma.mixeeva.kidsplay.screens.GiftInfoScreen
import rimma.mixeeva.kidsplay.screens.ShowGetAchievement
import rimma.mixeeva.kidsplay.screens.YouReceivedGiftScreen
import rimma.mixeeva.kidsplay.screens.colorGame.ColorGameFirstLevelsScreen
import rimma.mixeeva.kidsplay.screens.colorGame.ColorGameFourthLevelsScreen
import rimma.mixeeva.kidsplay.screens.colorGame.ColorGameSecondLevelsScreen
import rimma.mixeeva.kidsplay.screens.colorGame.ColorGameThirdLevelsScreen
import rimma.mixeeva.kidsplay.screens.parentsPart.ChildAchievementsStatisticsScreen
import rimma.mixeeva.kidsplay.screens.parentsPart.ChildAttributesScreen
import rimma.mixeeva.kidsplay.screens.parentsPart.ChildColorGameLevelsStatisticsScreen
import rimma.mixeeva.kidsplay.screens.parentsPart.ChildGiftsStatisticsScreen
import rimma.mixeeva.kidsplay.screens.parentsPart.ChildrenScreen
import rimma.mixeeva.kidsplay.screens.parentsPart.LoginScreen
import rimma.mixeeva.kidsplay.screens.parentsPart.RegistrationScreen
import rimma.mixeeva.kidsplay.ui.theme.KidsPlayTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var mediaPlayer: KidsMediaPlayer

    @Inject
    lateinit var navigator: Navigator

    private val mainViewModel by viewModels<MainViewModel>()
    private val colorGameViewModel by viewModels<ColorGameViewModel>()
    private val parentViewModel by viewModels<ParentViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация и запуск музыки
        mediaPlayer.playSong(R.raw.greeting, true)
        mainViewModel.startObservation()
        enableEdgeToEdge()
        setContent {
            val controller = rememberNavController()
            navigator.setController(controller)
            KidsPlayTheme {
                NavHost(navController = controller, startDestination = Screen.GreetingScreen) {
                    composable<Screen.GreetingScreen> {
                      GreetingScreen(viewModel = mainViewModel)
                    }
                    composable<Screen.PlayGroundScreen> {
                        PlaygroundScreen(mainViewModel)
                    }
                    composable<Screen.ColorGameScreen> {
                        ColorGameScreen(colorGameViewModel)
                    }
                    composable<Screen.ChooseAvatarScreen> {
                        ChooseAvatarScreen(mainViewModel)
                    }
                    composable<Screen.ChooseNicknameScreen> {
                        val args = it.toRoute<Screen.ChooseNicknameScreen>()
                        ChooseNicknameScreen(mainViewModel, args.avatar)
                    }
                    composable<Screen.KidAccountScreen> {
                        val args = it.toRoute<Screen.KidAccountScreen>()
                        KidAccountScreen(mainViewModel, args.avatar, args.nickname)
                    }
                    composable<Screen.GiftScreen> {
                        GiftScreen(mainViewModel)
                    }
                    composable<Screen.GiftInfoScreen> {
                        val args = it.toRoute<Screen.GiftInfoScreen>()
                        GiftInfoScreen(
                            viewModel = mainViewModel,
                            id = args.id,
                        )
                    }
                    composable<Screen.AchievementScreen> {
                        AchievementScreen(mainViewModel)
                    }
                    composable<Screen.AchievementInfoScreen> {
                        val args = it.toRoute<Screen.AchievementInfoScreen>()
                        AchievementInfoScreen(
                            viewModel = mainViewModel,
                            id = args.id
                        )
                    }
                    composable<Screen.ColorGameFirstLevelsScreen> {
                        ColorGameFirstLevelsScreen(colorGameViewModel)
                    }
                    composable<Screen.ColorGameSecondLevelsScreen> {
                        ColorGameSecondLevelsScreen(colorGameViewModel)
                    }
                    composable<Screen.ColorGameThirdLevelsScreen> {
                        ColorGameThirdLevelsScreen(colorGameViewModel)
                    }
                    composable<Screen.ColorGameFourthLevelsScreen> {
                        ColorGameFourthLevelsScreen(colorGameViewModel)
                    }
                    composable<Screen.ColorLevelScreen> {
                        val args = it.toRoute<Screen.AchievementInfoScreen>()
                        ColorLevelScreen(colorGameViewModel, mainViewModel, args.id)
                    }
                    composable<Screen.YouReceivedGiftScreen> {
                        YouReceivedGiftScreen(mainViewModel)
                    }
                    composable<Screen.LoginScreen> {
                        LoginScreen(parentViewModel)
                    }
                    composable<Screen.RegistrationScreen> {
                        RegistrationScreen(parentViewModel)
                    }
                    composable<Screen.ChildrenScreen> {
                        ChildrenScreen(parentViewModel)
                    }
                    composable<Screen.ChildAttributesScreen> {
                        val args = it.toRoute<Screen.ChildAttributesScreen>()
                        ChildAttributesScreen(parentViewModel, args.username, args.avatar)
                    }
                    composable<Screen.ChildAchievementsStatisticsScreen> {
                        ChildAchievementsStatisticsScreen(parentViewModel)
                    }
                    composable<Screen.ChildGiftsStatisticsScreen> {
                        ChildGiftsStatisticsScreen(parentViewModel)
                    }
                    composable<Screen.ChildColorGameLevelsStatisticsScreen> {
                        ChildColorGameLevelsStatisticsScreen(parentViewModel)
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 100.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                ShowGetAchievement(mainViewModel)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.stopObservation()
        mediaPlayer.destroy()
        navigator.clear()
    }
}


