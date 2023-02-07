package burujiyaseer.example.anagramsolver.feature_dictionary.domain.repository

import burujiyaseer.example.anagramsolver.core.utils.Resource
import burujiyaseer.example.anagramsolver.feature_dictionary.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getWordInfo(word: String): Resource<List<WordInfo>>
}