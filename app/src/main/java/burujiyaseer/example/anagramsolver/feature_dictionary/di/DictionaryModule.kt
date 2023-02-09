package burujiyaseer.example.anagramsolver.feature_dictionary.di

import burujiyaseer.example.anagramsolver.feature_dictionary.data.remote.DictionaryApi
import burujiyaseer.example.anagramsolver.feature_dictionary.data.repository.RepositoryImpl
import burujiyaseer.example.anagramsolver.feature_dictionary.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DictionaryModule {

    @Singleton
    @Provides
    fun provideRepository(dictionaryApi: DictionaryApi): Repository {
        return RepositoryImpl(dictionaryApi)
    }

    @Singleton
    @Provides
    fun provideDictionaryApi(okHttpClient: OkHttpClient.Builder): DictionaryApi {
        return Retrofit.Builder()
            .baseUrl(DictionaryApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()
            .create(DictionaryApi::class.java)
    }

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient.Builder{
        return OkHttpClient.Builder()
            .cache(cache = null)
            .readTimeout(100, TimeUnit.SECONDS)
            .connectTimeout(100, TimeUnit.SECONDS)
            .protocols(listOf(Protocol.HTTP_1_1))
            .retryOnConnectionFailure(true)
//            .callTimeout(100, TimeUnit.SECONDS)
//            .connectTimeout(100, TimeUnit.SECONDS)
//            .readTimeout(100, TimeUnit.SECONDS)
//            .writeTimeout(30, TimeUnit.SECONDS)
    }
}