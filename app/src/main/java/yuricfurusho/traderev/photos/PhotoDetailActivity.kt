package yuricfurusho.traderev.photos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_photo_detail.*
import yuricfurusho.traderev.R

class PhotoDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)

        val unsplashPhoto = intent.getSerializableExtra(EXTRA_UNSPLASH_PHOTO) as? UnsplashPhoto
            ?: UnsplashPhoto()

        //TODO add loading
        Glide.with(this).load(unsplashPhoto.urls.full).into(photo)
        username.text = unsplashPhoto.user.username
        unsplashPhoto.likes?.let { likes.text = getString(R.string.likes, it.toString()) }
        description.text = unsplashPhoto.description
    }

    companion object {
        const val EXTRA_UNSPLASH_PHOTO = "extra_unsplashPhoto"
    }
}
