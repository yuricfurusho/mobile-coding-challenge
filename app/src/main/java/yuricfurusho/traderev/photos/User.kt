package yuricfurusho.traderev.photos

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class User(
    val username: String = ""
) : Serializable
