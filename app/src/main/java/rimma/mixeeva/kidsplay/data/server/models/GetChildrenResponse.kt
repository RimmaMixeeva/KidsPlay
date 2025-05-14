package rimma.mixeeva.kidsplay.data.server.models

import kotlinx.serialization.Serializable


@Serializable
data class GetChildrenResponse(
    val list: List<String>,
)