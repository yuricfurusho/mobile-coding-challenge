package yuricfurusho.traderev.photos

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class UnsplashPhotoUrls(
        val full: String = "",
        val thumb: String = ""
) : Serializable
