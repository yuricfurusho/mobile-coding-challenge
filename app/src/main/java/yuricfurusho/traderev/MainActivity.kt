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
                Photo("https://picsum.photos/200/300"),
                Photo("https://picsum.photos/300/200"),
                Photo("https://picsum.photos/1500/2000"),
                Photo("https://picsum.photos/200/400"),
                Photo("https://picsum.photos/400/200"),
                Photo("https://picsum.photos/200/600"),
                Photo("https://picsum.photos/600/300"),
                Photo("https://picsum.photos/1000/800"),
                Photo("https://picsum.photos/500/400"),
                Photo("https://picsum.photos/600/700")
            )
        )
    }
}