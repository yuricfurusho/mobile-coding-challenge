package yuricfurusho.traderev.photos

import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

private const val PHOTOS_PER_PAGE = 10

@Singleton
class PhotoRepository @Inject constructor(
    private val unsplashService: UnsplashService
) {
    var nextPage = 1


    fun getFirstPage(): Single<List<UnsplashPhoto>> {
        nextPage = 1
        return unsplashService.getPhotos(nextPage++, PHOTOS_PER_PAGE)
    }

    fun getNextPage() = unsplashService.getPhotos(nextPage++, PHOTOS_PER_PAGE)
}

