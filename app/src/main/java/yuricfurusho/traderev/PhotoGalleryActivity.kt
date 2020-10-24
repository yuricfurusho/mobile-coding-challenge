package yuricfurusho.traderev

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_photo_gallery.*
import yuricfurusho.traderev.photos.PhotoGalleryViewModel
import javax.inject.Inject

private const val SPAN_COUNT = 2

class PhotoGalleryActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<PhotoGalleryViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as TradeRevApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_gallery)

        recycler_photos.layoutManager = StaggeredGridLayoutManager(
            SPAN_COUNT,
            StaggeredGridLayoutManager.VERTICAL
        )
        recycler_photos.adapter = PhotoAdapter()

        viewModel.onCreate()

        viewModel.state.observe(this, {
            when (it) {
                is PhotoGalleryViewModel.State.Success ->
                    (recycler_photos.adapter as PhotoAdapter).setList(it.photoList)
                is PhotoGalleryViewModel.State.Error -> TODO()
            }
        })

    }
}
