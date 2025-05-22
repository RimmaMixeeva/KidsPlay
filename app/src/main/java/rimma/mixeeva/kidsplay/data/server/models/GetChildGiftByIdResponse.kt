package rimma.mixeeva.kidsplay.data.server.models

data class GetChildGiftByIdResponse(
    val username: String,
    val descriptionId: Int,
    val obtained: Boolean,
    val opened: Boolean,
    val used: Boolean
)
