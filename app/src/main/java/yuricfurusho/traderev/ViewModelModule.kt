package yuricfurusho.traderev

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.MapKey
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import kotlin.reflect.KClass

/**
 * Module which provides a [ViewModelProvider.Factory] which can be used to
 * create [ViewModel]s which have a constructor injected by Dagger.
 */
@Module
class ViewModelModule {

    @Provides
    fun provideViewModelFactory(
        viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
    ): ViewModelProvider.Factory = ViewModelFactory(viewModels)
}


/**
 * Apply this annotation to [Provides] methods which are annotated with
 * [dagger.multibindings.IntoMap] to allow the provided [ViewModel] to be
 * created by [ViewModelModule].
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@kotlin.annotation.Retention(AnnotationRetention.BINARY)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
