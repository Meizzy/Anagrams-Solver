package burujiyaseer.example.anagramsolver.feature_anagram.di

import burujiyaseer.example.anagramsolver.feature_anagram.data.network.WordApi
import burujiyaseer.example.anagramsolver.feature_anagram.data.network.WordsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideTargetApi(
        wordsService: WordsService
    ): WordApi {
        return wordsService.buildApi(WordApi::class.java)
    }
}