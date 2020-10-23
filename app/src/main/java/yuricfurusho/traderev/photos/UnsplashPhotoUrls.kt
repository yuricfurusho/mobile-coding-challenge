package yuricfurusho.traderev.photos

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UnsplashPhotoUrls(
        val full: String = "",
        val thumb: String = ""
)
