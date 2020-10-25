package yuricfurusho.traderev

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

@Module
class ThreadingModule {

    @Provides
    @Named(IO)
    fun provideIoScheduler(): Scheduler = Schedulers.io()

    @Provides
    @Named(UI)
    fun provideUiScheduler(): Scheduler = AndroidSchedulers.mainThread()

    companion object {
        const val IO = "io"
        const val UI = "ui"
    }
}
