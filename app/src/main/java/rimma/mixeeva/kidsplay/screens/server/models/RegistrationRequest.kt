package rimma.mixeeva.kidsplay.screens.server.models

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequest (
    val username: String,
    val email: String,
    val password: String
)