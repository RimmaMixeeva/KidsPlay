package rimma.mixeeva.kidsplay.navigation

import android.transition.Scene
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {

    //child part
    @Serializable
    data object GreetingScreen : Screen

    @Serializable
    data object ColorGameScreen : Screen

    @Serializable
    data object PlayGroundScreen : Screen

    @Serializable
    data object ChooseAvatarScreen : Screen

    @Serializable
    data class ChooseNicknameScreen(
        val avatar: Int
    ) : Screen

    @Serializable
    data class KidAccountScreen(
        val avatar: Int? = null,
        val nickname: String? = null
    ) : Screen

    @Serializable
    data object GiftScreen : Screen

    @Serializable
    data object AchievementScreen : Screen

    @Serializable
    data class AchievementInfoScreen(
        val id: Int
    ) : Screen

    @Serializable
    data class GiftInfoScreen(
        var id: Int,
    ) : Screen

    @Serializable
    data object ColorGameFirstLevelsScreen : Screen

    @Serializable
    data object ColorGameSecondLevelsScreen : Screen

    @Serializable
    data object ColorGameThirdLevelsScreen : Screen

    @Serializable
    data object ColorGameFourthLevelsScreen : Screen

    @Serializable
    data class ColorLevelScreen(
        var id: Int
    ) : Screen

    @Serializable
    data object YouReceivedGiftScreen : Screen

    //parents part
    @Serializable
    data object LoginScreen : Screen

    @Serializable
    data object RegistrationScreen : Screen

    @Serializable
    data object ChildrenScreen : Screen

    @Serializable
    data class ChildAttributesScreen(
        var username: String,
        var avatar: Int,
    ) : Screen

    @Serializable
    data object ChildAchievementsStatisticsScreen: Screen

    @Serializable
    data object ChildGiftsStatisticsScreen: Screen

    @Serializable
    data object ChildColorGameLevelsStatisticsScreen: Screen


}