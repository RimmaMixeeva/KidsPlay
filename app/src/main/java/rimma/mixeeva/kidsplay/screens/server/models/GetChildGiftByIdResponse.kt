package rimma.mixeeva.kidsplay.screens.server.models

data class GetChildGiftByIdResponse(
    val username: String,
    val title: String,
    val condition: String,
    val description: String,
    val obtained: Boolean,
    val opened: Boolean,
    val used: Boolean
)
