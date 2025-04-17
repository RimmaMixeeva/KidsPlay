package rimma.mixeeva.kidsplay.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {

    @Serializable
    data object GreetingScreen: Screen

    @Serializable
    data object ColorGameScreen: Screen

    @Serializable
    data object PlayGroundScreen: Screen

    @Serializable
    data object ChooseAvatarScreen: Screen

    @Serializable
    data object ChooseNicknameScreen: Screen

    @Serializable
    data object KidAccountScreen: Screen

}