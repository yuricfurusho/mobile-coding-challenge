package yuricfurusho.traderev

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_photo_gallery.*
import yuricfurusho.traderev.photos.PhotoGalleryViewModel
import yuricfurusho.traderev.photos.UnsplashService
import javax.inject.Inject

private const val SPAN_COUNT = 2

class PhotoGalleryActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<PhotoGalleryViewModel> { viewModelFactory }

    private val compositeDisposable = CompositeDisposable() //TODO: move away from ui into viewModel or repository
    private val unsplashService = UnsplashService.create() //TODO: remove later: for testing only

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as TradeRevApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_gallery)

        recycler_photos.layoutManager = StaggeredGridLayoutManager(
            SPAN_COUNT,
            StaggeredGridLayoutManager.VERTICAL
        )
        recycler_photos.adapter = PhotoAdapter()


        unsplashService.getPhotos(1, 10) //TODO: move away from ui into viewModel or repository
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ photoList ->
                    (recycler_photos.adapter as PhotoAdapter).setList(
                            photoList.map { it.thumb }
                    )
                }, {
                    Log.e("MainActivity", it.localizedMessage ?: "")
                })
                .also {
                    compositeDisposable.add(it)
                }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear() //TODO: move away from ui into viewModel or repository
    }
}
