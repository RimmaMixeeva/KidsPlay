package rimma.mixeeva.kidsplay.data.server.models

data class CreateUpdateGiftRequest(
    val username: String,
    val title: String,
    val condition: String,
    val description: String,
    val obtained: Boolean,
    val opened: Boolean,
    val used: Boolean
)
