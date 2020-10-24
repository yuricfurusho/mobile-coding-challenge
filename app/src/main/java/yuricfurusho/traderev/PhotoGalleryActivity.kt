package yuricfurusho.traderev

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_photo_gallery.*
import yuricfurusho.traderev.photos.PhotoDetailActivity
import yuricfurusho.traderev.photos.PhotoDetailActivity.Companion.EXTRA_POSITION
import yuricfurusho.traderev.photos.PhotoDetailActivity.Companion.EXTRA_UNSPLASH_PHOTO
import yuricfurusho.traderev.photos.PhotoGalleryViewModel
import yuricfurusho.traderev.photos.PhotoRepository.Companion.PHOTOS_PER_PAGE
import yuricfurusho.traderev.photos.UnsplashPhoto
import javax.inject.Inject

private val TAG = PhotoGalleryActivity::class.java.simpleName

class PhotoGalleryActivity : AppCompatActivity(), PhotoAdapter.PhotoAdapterListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<PhotoGalleryViewModel> { viewModelFactory }

    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as TradeRevApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_gallery)

        photoAdapter = PhotoAdapter(this)
        gridLayoutManager = GridLayoutManager(this, resources.getInteger(R.integer.spanCount))
        //TODO try a better layout
        recycler_photos.apply {
            adapter = photoAdapter
            layoutManager = gridLayoutManager
            addOnScrollListener(onScrollListener())
        }

        viewModel.onCreate()

        viewModel.state.observe(this, {
            when (it) {
                is PhotoGalleryViewModel.State.Success -> photoAdapter.setList(it.photoList)
                is PhotoGalleryViewModel.State.Error -> Log.e(TAG, it.error.localizedMessage ?: "")
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

    override fun onItemClick(unsplashPhotoList: List<UnsplashPhoto>, position: Int) {
        //TODO add ripple animation
        //TODO convert to the intent pattern through viewModel
        startActivity(
            Intent(this, PhotoDetailActivity::class.java).apply {
                putParcelableArrayListExtra(EXTRA_UNSPLASH_PHOTO, ArrayList(unsplashPhotoList))
                putExtra(EXTRA_POSITION, position)
            }
        )
    }
}
