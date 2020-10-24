package yuricfurusho.traderev.photos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PhotoGalleryViewModel(
    private val photoRepository: PhotoRepository
) : ViewModel() {

    sealed class State {
        data class Success(val photoList: List<String>) : State()
        data class Error(val error: Throwable) : State()
    }

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private val compositeDisposable = CompositeDisposable()

    private val thumbList = mutableListOf<String>()

    fun onCreate() {
        reloads()
    }

    private fun reloads() {
        photoRepository.getFirstPage()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { firstPageOfPhotoList -> firstPageOfPhotoList.map { it.thumb } }
            .subscribe({ firstPageOfThumbs ->
                thumbList.clear()
                thumbList.addAll(firstPageOfThumbs)
                _state.postValue(State.Success(thumbList))
            }, {
                _state.postValue(State.Error(it))
            })
            .also {
                compositeDisposable.add(it)
            }
    }

    fun loadNextPage() {
        photoRepository.getNextPage()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { nextPageOfPhotoList -> nextPageOfPhotoList.map { it.thumb } }
            .subscribe({ nextPageOfThumbs ->
                thumbList.addAll(nextPageOfThumbs)
                _state.postValue(State.Success(thumbList))
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
