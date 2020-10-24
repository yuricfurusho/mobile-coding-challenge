package yuricfurusho.traderev.photos

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UnsplashPhotoUrls(
        val full: String? = "",
        val thumb: String? = ""
) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(full)
                parcel.writeString(thumb)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<UnsplashPhotoUrls> {
                override fun createFromParcel(parcel: Parcel): UnsplashPhotoUrls {
                        return UnsplashPhotoUrls(parcel)
                }

                override fun newArray(size: Int): Array<UnsplashPhotoUrls?> {
                        return arrayOfNulls(size)
                }
        }
}
