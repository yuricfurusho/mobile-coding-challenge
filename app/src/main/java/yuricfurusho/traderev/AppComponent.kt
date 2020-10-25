package yuricfurusho.traderev

import dagger.Component
import yuricfurusho.traderev.network.NetworkModule
import yuricfurusho.traderev.photos.PhotoGalleryModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        PhotoGalleryModule::class,
        ViewModelModule::class,
        ThreadingModule::class
    ]
)
interface AppComponent {

    fun inject(fragment: PhotoGalleryActivity)
}
