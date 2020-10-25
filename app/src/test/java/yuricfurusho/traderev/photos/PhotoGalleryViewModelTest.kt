package yuricfurusho.traderev.photos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

private val FIRST_PAGE = listOf(
    UnsplashPhoto(null, "1", null, null)
)
private val SECOND_PAGE = listOf(
    UnsplashPhoto(null, "2", null, null)
)

@RunWith(MockitoJUnitRunner::class)
class PhotoGalleryViewModelTest {

    @get:Rule val rule = InstantTaskExecutorRule()

    @Mock private lateinit var photoRepository: PhotoRepository

    private lateinit var viewModel: PhotoGalleryViewModel

    private lateinit var firstPage: Single<List<UnsplashPhoto>>
    private lateinit var secondPage: Single<List<UnsplashPhoto>>

    @Before
    fun setUp() {
        viewModel = PhotoGalleryViewModel(
            photoRepository,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )

        firstPage = Single.just(FIRST_PAGE)
        secondPage = Single.just(SECOND_PAGE)
    }

    @Test
    fun successfullyRetrievesFirstPage() {
        whenever(photoRepository.getFirstPage()).thenReturn(firstPage)

        viewModel.onCreate()

        assertEquals(PhotoGalleryViewModel.State.Success(FIRST_PAGE), viewModel.state.value!!)
    }

    @Test
    fun loadNextPage() {
        whenever(photoRepository.getFirstPage()).thenReturn(firstPage)
        whenever(photoRepository.getNextPage()).thenReturn(secondPage)
        val combinedPhotoList = mutableListOf<UnsplashPhoto>()
        combinedPhotoList.addAll(FIRST_PAGE)
        combinedPhotoList.addAll(SECOND_PAGE)

        viewModel.onCreate()
        assertEquals(PhotoGalleryViewModel.State.Success(FIRST_PAGE), viewModel.state.value!!)

        viewModel.loadNextPage()
        assertEquals(
            PhotoGalleryViewModel.State.Success(combinedPhotoList),
            viewModel.state.value!!
        )

    }
}