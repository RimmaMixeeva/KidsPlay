package rimma.mixeeva.kidsplay.data.server.models

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationResponse (
    val token: String
)