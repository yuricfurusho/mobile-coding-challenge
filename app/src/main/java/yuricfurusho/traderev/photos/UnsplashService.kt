package yuricfurusho.traderev.photos

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import yuricfurusho.traderev.BuildConfig

interface UnsplashService {

    @GET("/photos")
    fun getPhotos(
            @Query("page") page: Int,
            @Query("per_page") perPage: Int,
            @Query("client_id") clientId: String = BuildConfig.UNSPLASH_ACCESS_KEY
    ): Single<UnsplashPhotoResponse>

    companion object {
        private const val BASE_URL = "https://api.unsplash.com/"

        fun create(): UnsplashService {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }

            val client = OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .build()

            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()
                    .create(UnsplashService::class.java)
        }
    }
}
