package yuricfurusho.traderev.photos

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UnsplashPhoto(
    val urls: UnsplashPhotoUrls = UnsplashPhotoUrls("",""),
    val description: String? = null
) {
    val thumb: String
        get() = urls.thumb
}
