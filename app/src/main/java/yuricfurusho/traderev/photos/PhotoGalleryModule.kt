package yuricfurusho.traderev.photos

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.reactivex.Scheduler
import yuricfurusho.traderev.ThreadingModule
import yuricfurusho.traderev.ViewModelKey
import javax.inject.Named

@Module
class PhotoGalleryModule {

    @Provides
    @IntoMap
    @ViewModelKey(PhotoGalleryViewModel::class)
    fun providesPhotoGalleryViewModel(
        photoRepository: PhotoRepository,
        @Named(ThreadingModule.IO) ioScheduler: Scheduler,
        @Named(ThreadingModule.UI) uiScheduler: Scheduler
    ): ViewModel = PhotoGalleryViewModel(
        photoRepository,
        ioScheduler,
        uiScheduler
    )
}
