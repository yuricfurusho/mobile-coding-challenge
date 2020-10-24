package yuricfurusho.traderev.photos

import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val unsplashApi: UnsplashService
)
