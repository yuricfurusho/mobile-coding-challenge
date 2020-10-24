package yuricfurusho.traderev

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import kotlinx.android.synthetic.main.activity_photo_gallery.*
import yuricfurusho.traderev.photos.PhotoGalleryViewModel
import javax.inject.Inject

private const val SPAN_COUNT = 2

class PhotoGalleryActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<PhotoGalleryViewModel> { viewModelFactory }

    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as TradeRevApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_gallery)

        photoAdapter = PhotoAdapter()
        staggeredGridLayoutManager = StaggeredGridLayoutManager(SPAN_COUNT, VERTICAL)
        staggeredGridLayoutManager.gapStrategy = GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        recycler_photos.apply {
            adapter = photoAdapter
            layoutManager = staggeredGridLayoutManager
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
                val lastVisibleItemsIndexes =
                    staggeredGridLayoutManager.findLastVisibleItemPositions(null)
                val lastLoadedItemIndex = (photoAdapter.itemCount - 1).coerceAtLeast(0)
                if (lastVisibleItemsIndexes.contains(lastLoadedItemIndex)) {
                    viewModel.loadNextPage()
                }
            }
        }
    }
}
