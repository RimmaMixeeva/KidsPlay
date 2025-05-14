package rimma.mixeeva.kidsplay.data.server.models

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequest (
    val username: String,
    val email: String,
    val password: String
)