package burujiyaseer.example.anagramsolver.feature_anagram.data.repository

import burujiyaseer.example.anagramsolver.core.utils.SafeApiCall
import burujiyaseer.example.anagramsolver.feature_anagram.data.network.WordApi
import javax.inject.Inject

class Repository @Inject constructor(private val wordApi: WordApi) : SafeApiCall {

    suspend fun getAnagramsWordsList(query: String) = safeApiCall { wordApi.getAnagramWords(query) }

}