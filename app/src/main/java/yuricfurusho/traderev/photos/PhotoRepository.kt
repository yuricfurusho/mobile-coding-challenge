package yuricfurusho.traderev.photos

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepository @Inject constructor(
    private val unsplashService: UnsplashService
) {
    fun getFirstPage() = unsplashService.getPhotos(1, 10)
}

