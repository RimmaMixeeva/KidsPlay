package rimma.mixeeva.kidsplay.data.objects

import kotlinx.serialization.Serializable

@Serializable
data class Gift(
    var id: Int,
    var title: String = "",
    var condition: String = "",
    var description: String = "",
    var obtained: Boolean = false,
    var opened: Boolean = false,
    var used: Boolean = false,
    var executor: String? = null,
    var intelligence: Int = 0,
    var attentiveness: Int = 0,
    var reaction: Int = 0,
    var logic: Int = 0,
    var coins: Int = 0
)
