package rimma.mixeeva.kidsplay.screens.server.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse (
    val token: String
)