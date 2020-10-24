package yuricfurusho.traderev.photos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_photo_detail.*
import yuricfurusho.traderev.PhotoDetailAdapter
import yuricfurusho.traderev.R

class PhotoDetailActivity : AppCompatActivity() {

    private var photoList: ArrayList<UnsplashPhoto>? = ArrayList()
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)

        photoList = intent.getParcelableArrayListExtra(EXTRA_UNSPLASH_PHOTO)
        position = intent.getIntExtra(EXTRA_POSITION, 0)

        photoViewPager.adapter = PhotoDetailAdapter().apply {
            photoList?.let { setList(it) }
        }
        photoViewPager.currentItem = position

    }

    companion object {
        const val EXTRA_UNSPLASH_PHOTO = "extra_unsplashPhoto"
        const val EXTRA_POSITION = "extra_position"
    }
}
