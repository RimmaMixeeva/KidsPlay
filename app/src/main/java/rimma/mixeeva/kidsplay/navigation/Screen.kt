package rimma.mixeeva.kidsplay.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {

    @Serializable
    object GreetingScreen: Screen

    @Serializable
    object ColorGameScreen: Screen

    @Serializable
    object PlayGroundScreen: Screen

    @Serializable
    object ChooseAvatarScreen: Screen

    @Serializable
    object ChooseNicknameScreen: Screen

}