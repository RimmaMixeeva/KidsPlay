package rimma.mixeeva.kidsplay.screens.server.models

import kotlinx.serialization.Serializable

@Serializable
data class GetChildAttributesResponse(
    val username: String,
    val intelligence: Int,
    val attentiveness: Int,
    val reaction: Int,
    val logic: Int,
    val coins: Int
)