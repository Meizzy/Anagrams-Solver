package burujiyaseer.example.anagramsolver.feature_dictionary.di

import burujiyaseer.example.anagramsolver.feature_dictionary.data.remote.DictionaryApi
import burujiyaseer.example.anagramsolver.feature_dictionary.data.repository.RepositoryImpl
import burujiyaseer.example.anagramsolver.feature_dictionary.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideDictionaryApi(): DictionaryApi {
        return Retrofit.Builder()
            .baseUrl(DictionaryApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }
}