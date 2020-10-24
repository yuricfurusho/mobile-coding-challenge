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

        val urlFull = intent.getStringExtra(EXTRA_URL_FULL) ?: ""

        //TODO add loading
        Glide.with(this).load(urlFull).into(photo)
    }

    companion object {
        const val EXTRA_URL_FULL = "extra_url_full"
    }
}
