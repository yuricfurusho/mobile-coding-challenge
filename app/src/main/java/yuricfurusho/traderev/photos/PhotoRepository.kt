package yuricfurusho.traderev.photos

import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PhotoRepository @Inject constructor(
    private val unsplashService: UnsplashService
) {
    private var nextPage = 1


    fun getFirstPage(): Single<List<UnsplashPhoto>> {
        nextPage = 1
        return unsplashService.getPhotos(nextPage++, PHOTOS_PER_PAGE)
    }

    fun getNextPage() = unsplashService.getPhotos(nextPage++, PHOTOS_PER_PAGE)

    companion object {
        const val PHOTOS_PER_PAGE = 40
    }
}

