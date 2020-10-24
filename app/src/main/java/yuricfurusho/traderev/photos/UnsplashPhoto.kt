package yuricfurusho.traderev.photos

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class UnsplashPhoto(
    val urls: UnsplashPhotoUrls = UnsplashPhotoUrls("", ""),
    val description: String? = "",
    val likes: Int? = null,
    val user: User = User("")
) : Serializable {
    val thumb: String
        get() = urls.thumb
}
