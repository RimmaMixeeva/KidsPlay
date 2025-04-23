package rimma.mixeeva.kidsplay.navigation

import kotlinx.serialization.Serializable
import rimma.mixeeva.kidsplay.data.objects.Achievement
import rimma.mixeeva.kidsplay.data.objects.Gift
import java.util.concurrent.Executor

@Serializable
sealed interface Screen {

    @Serializable
    data object GreetingScreen : Screen

    @Serializable
    data object ColorGameScreen : Screen

    @Serializable
    data object PlayGroundScreen : Screen

    @Serializable
    data object ChooseAvatarScreen : Screen

    @Serializable
    data object ChooseNicknameScreen : Screen

    @Serializable
    data object KidAccountScreen : Screen

    @Serializable
    data object GiftScreen : Screen

    @Serializable
    data object AchievementScreen : Screen

    @Serializable
    data class AchievementInfoScreen(
        val title: String,
        var condition: String,
        var description: String
    ) : Screen

    @Serializable
    data class GiftInfoScreen(
        val title: String,
        var condition: String,
        var description: String,
        var executor: String?,
        var id: Int,
        var intelligence: Int = 0,
        var attentiveness: Int = 0,
        var reaction: Int = 0,
        var logic: Int = 0,
        var coins: Int = 0,
    ) : Screen

}