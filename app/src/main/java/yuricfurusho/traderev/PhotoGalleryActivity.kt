package yuricfurusho.traderev

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_photo_gallery.*
import yuricfurusho.traderev.photos.PhotoGalleryViewModel
import yuricfurusho.traderev.photos.PhotoRepository.Companion.PHOTOS_PER_PAGE
import javax.inject.Inject

private const val SPAN_COUNT = 2

class PhotoGalleryActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<PhotoGalleryViewModel> { viewModelFactory }

    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as TradeRevApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_gallery)

        photoAdapter = PhotoAdapter()
        gridLayoutManager = GridLayoutManager(this, SPAN_COUNT)
        recycler_photos.apply {
            adapter = photoAdapter
            layoutManager = gridLayoutManager
            addOnScrollListener(onScrollListener())
        }

        viewModel.onCreate()

        viewModel.state.observe(this, {
            when (it) {
                is PhotoGalleryViewModel.State.Success -> photoAdapter.setList(it.photoList)
                is PhotoGalleryViewModel.State.Error -> TODO()
            }
        })

    }

    private fun onScrollListener(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition()
                val lastLoadedItemIndex = (photoAdapter.itemCount - 1).coerceAtLeast(0)
                if (lastVisibleItemPosition > lastLoadedItemIndex - PHOTOS_PER_PAGE / 2) {
                    viewModel.loadNextPage()
                }
            }
        }
    }
}
