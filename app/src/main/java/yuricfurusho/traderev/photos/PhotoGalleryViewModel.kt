package yuricfurusho.traderev.photos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class PhotoGalleryViewModel(
    private val photoRepository: PhotoRepository,
    private val ioScheduler: Scheduler,
    private val uiScheduler: Scheduler
) : ViewModel() {

    sealed class State {
        data class Success(val photoList: List<UnsplashPhoto>) : State()
        data class Error(val error: Throwable) : State()
    }

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private val compositeDisposable = CompositeDisposable()

    private val unsplashPhotoList = mutableListOf<UnsplashPhoto>()

    fun onCreate() {
        reloads()
    }

    private fun reloads() {
        photoRepository.getFirstPage()
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe({ firstPageOfPhotos ->
                unsplashPhotoList.clear()
                unsplashPhotoList.addAll(firstPageOfPhotos)
                _state.postValue(State.Success(unsplashPhotoList))
            }, {
                _state.postValue(State.Error(it))
            })
            .also {
                compositeDisposable.add(it)
            }
    }

    fun loadNextPage() {
        photoRepository.getNextPage()
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe({ nextPageOfPhotos ->
                unsplashPhotoList.addAll(nextPageOfPhotos)
                _state.postValue(State.Success(unsplashPhotoList))
            }, {
                _state.postValue(State.Error(it))
            })
            .also {
                compositeDisposable.add(it)
            }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
