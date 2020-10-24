package yuricfurusho.traderev

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider

/**
 * [ViewModelProvider.Factory] which can create [ViewModel]s which have a
 * constructor injected by Dagger.
 */
class ViewModelFactory(
    private val viewModels: Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val provider = checkNotNull(viewModels[modelClass]) {
            "No Provider found for ${modelClass.simpleName}. " +
                    "Is there a @Provides method defined for it?"
        }

        @Suppress("UNCHECKED_CAST")
        return provider.get() as T
    }
}
