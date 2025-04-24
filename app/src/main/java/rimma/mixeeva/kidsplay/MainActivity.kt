package rimma.mixeeva.kidsplay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import rimma.mixeeva.kidsplay.screens.ColorGameScreen
import rimma.mixeeva.kidsplay.screens.GiftScreen
import rimma.mixeeva.kidsplay.screens.GreetingScreen
import rimma.mixeeva.kidsplay.screens.KidAccountScreen
import rimma.mixeeva.kidsplay.screens.PlaygroundScreen
import rimma.mixeeva.kidsplay.screens.AchievementInfoScreen
import rimma.mixeeva.kidsplay.screens.GiftInfoScreen
import rimma.mixeeva.kidsplay.ui.theme.KidsPlayTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var mediaPlayer: KidsMediaPlayer

    @Inject
    lateinit var navigator: Navigator

    val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация и запуск музыки
        mediaPlayer.playSong(R.raw.greeting, true)
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
                        ColorGameScreen()
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
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.destroy()
        navigator.clear()
    }
}


