package burujiyaseer.example.anagramsolver.feature_dictionary.data.repository

import burujiyaseer.example.anagramsolver.core.utils.Resource
import burujiyaseer.example.anagramsolver.core.utils.SafeApiCall
import burujiyaseer.example.anagramsolver.feature_dictionary.data.remote.DictionaryApi
import burujiyaseer.example.anagramsolver.feature_dictionary.domain.model.WordInfo
import burujiyaseer.example.anagramsolver.feature_dictionary.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryImpl(private val dictionaryApi: DictionaryApi) : Repository, SafeApiCall {

    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading)

        val remoteWordInfo = dictionaryApi.getWord(word)
        val result = safeApiCall { remoteWordInfo }

        if (result is Resource.Success) {
            emit(Resource.Success(remoteWordInfo.map { it.toWordInfo() }))
        } else {
            emit(result as Resource.Failure)
        }
    }
}