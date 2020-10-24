package yuricfurusho.traderev.photos

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UnsplashPhoto(
    val urls: UnsplashPhotoUrls? = UnsplashPhotoUrls("", ""),
    val description: String? = "",
    val likes: Int? = null,
    val user: User? = User("")
) : Parcelable {
    val thumb: String?
        get() = urls?.thumb

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(UnsplashPhotoUrls::class.java.classLoader),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable(User::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(urls, flags)
        parcel.writeString(description)
        parcel.writeValue(likes)
        parcel.writeParcelable(user, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UnsplashPhoto> {
        override fun createFromParcel(parcel: Parcel): UnsplashPhoto {
            return UnsplashPhoto(parcel)
        }

        override fun newArray(size: Int): Array<UnsplashPhoto?> {
            return arrayOfNulls(size)
        }
    }
}
