package yuricfurusho.traderev.photos

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import yuricfurusho.traderev.ViewModelKey

@Module
class PhotoGalleryModule {

    @Provides
    @IntoMap
    @ViewModelKey(PhotoGalleryViewModel::class)
    fun providesPhotoGalleryViewModel(
        photoRepository: PhotoRepository
    ): ViewModel = PhotoGalleryViewModel(
        photoRepository
    )
}
