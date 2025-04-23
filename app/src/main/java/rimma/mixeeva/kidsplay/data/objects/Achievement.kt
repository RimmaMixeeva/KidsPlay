package rimma.mixeeva.kidsplay.data.objects

import kotlinx.serialization.Serializable

@Serializable
data class Achievement(
    var title: String,
    var condition: String,
    var description: String,
    var obtained: Boolean
)
