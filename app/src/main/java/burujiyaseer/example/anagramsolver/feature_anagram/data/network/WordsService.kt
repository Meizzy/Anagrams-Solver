package burujiyaseer.example.anagramsolver.feature_anagram.data.network

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class WordsService @Inject constructor() {

    companion object {
        private const val BASE_URL = "http://anagramica.com"
    }

    fun <WordApi> buildApi(
        api: Class<WordApi>
    ): WordApi {
        return Retrofit.Builder().baseUrl(BASE_URL).client(getRetrofitClient())
            .addConverterFactory(GsonConverterFactory.create()).build().create(api)
    }

    private fun getRetrofitClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor { chain ->
            chain.proceed(chain.request().newBuilder().also {
                it.addHeader("Accept", "application/json")
            }.build())
        }.also { client ->
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                client.addInterceptor(logging)
            }
        }.build()
    }
}
