package yuricfurusho.traderev.network

import dagger.Module
import dagger.Provides
import yuricfurusho.traderev.photos.UnsplashService
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideUnsplashService(): UnsplashService {
        return UnsplashService.create()
    }
}
