package burujiyaseer.example.anagramsolver.feature_dictionary.data.repository

import burujiyaseer.example.anagramsolver.core.utils.Resource
import burujiyaseer.example.anagramsolver.core.utils.SafeApiCall
import burujiyaseer.example.anagramsolver.feature_dictionary.data.remote.DictionaryApi
import burujiyaseer.example.anagramsolver.feature_dictionary.domain.model.WordInfo
import burujiyaseer.example.anagramsolver.feature_dictionary.domain.repository.Repository

class RepositoryImpl(private val dictionaryApi: DictionaryApi) : Repository, SafeApiCall {

    override suspend fun getWordInfo(word: String): Resource<List<WordInfo>> =
        safeApiCall { dictionaryApi.getWord(word).map { it.toWordInfo() } }
}