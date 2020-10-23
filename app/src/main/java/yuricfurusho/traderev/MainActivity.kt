package yuricfurusho.traderev

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import yuricfurusho.traderev.photos.Photo

private const val SPAN_COUNT = 2

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_photos.layoutManager = StaggeredGridLayoutManager(
            SPAN_COUNT,
            StaggeredGridLayoutManager.VERTICAL
        )
        recycler_photos.adapter = PhotoAdapter()
        (recycler_photos.adapter as PhotoAdapter).setList(
            listOf(
                Photo("https://cdn.eso.org/images/screen/eso1907a.jpg")
            )
        )
    }
}