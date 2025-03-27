package rimma.mixeeva.kidsplay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import rimma.mixeeva.kidsplay.navigation.Navigator
import rimma.mixeeva.kidsplay.navigation.Screen
import rimma.mixeeva.kidsplay.screens.ChooseAvatarScreen
import rimma.mixeeva.kidsplay.screens.ChooseNicknameScreen
import rimma.mixeeva.kidsplay.screens.ColorGameScreen
import rimma.mixeeva.kidsplay.screens.GreetingScreen
import rimma.mixeeva.kidsplay.screens.PlaygroundScreen
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
                        GreetingScreen(
                            onPlay = { navigator.navigate(Screen.ChooseAvatarScreen) },
                            onParent = {}
                        )
                    }
                    composable<Screen.PlayGroundScreen> {
                        PlaygroundScreen(colorPlay = { navigator.navigate(Screen.ColorGameScreen) })
                    }
                    composable<Screen.ColorGameScreen> {
                        ColorGameScreen()
                    }
                    composable<Screen.ChooseAvatarScreen> {
                        ChooseAvatarScreen(mainViewModel)
                    }
                    composable<Screen.ChooseNicknameScreen> {
                        ChooseNicknameScreen()
                    }

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.destroy() // Освобождение ресурсов
        navigator.clear()
    }
}


