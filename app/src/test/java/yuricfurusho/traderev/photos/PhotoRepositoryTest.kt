package yuricfurusho.traderev.photos

import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

private val FIRST_PAGE = listOf(
    UnsplashPhoto(null, "1", null, null)
)
private val SECOND_PAGE = listOf(
    UnsplashPhoto(null, "2", null, null)
)
private val THIRD_PAGE = listOf(
    UnsplashPhoto(null, "3", null, null)
)

@RunWith(MockitoJUnitRunner::class)
class PhotoRepositoryTest {

    @Mock private lateinit var unsplashService: UnsplashService

    private lateinit var repository: PhotoRepository

    private lateinit var firstPage: Single<List<UnsplashPhoto>>
    private lateinit var secondPage: Single<List<UnsplashPhoto>>
    private lateinit var thirdPage: Single<List<UnsplashPhoto>>

    @Before
    fun setUp() {
        repository = PhotoRepository(unsplashService)

        firstPage = Single.just(FIRST_PAGE)
        secondPage = Single.just(SECOND_PAGE)
        thirdPage = Single.just(THIRD_PAGE)
    }

    @Test
    fun successfullyReturnsFirstPage() {
        whenever(unsplashService.getPhotos(eq(1), anyInt(), anyString()))
            .thenReturn(firstPage)


        assertEquals(firstPage, repository.getFirstPage())
        assertEquals(firstPage, repository.getFirstPage())
    }

    @Test
    fun successfullyReturnsNextPage() {
        whenever(unsplashService.getPhotos(eq(1), anyInt(), anyString()))
            .thenReturn(firstPage)
        whenever(unsplashService.getPhotos(eq(2), anyInt(), anyString()))
            .thenReturn(secondPage)
        whenever(unsplashService.getPhotos(eq(3), anyInt(), anyString()))
            .thenReturn(thirdPage)

        assertEquals(firstPage, repository.getNextPage())
        assertEquals(secondPage, repository.getNextPage())
        assertEquals(thirdPage, repository.getNextPage())
    }
}