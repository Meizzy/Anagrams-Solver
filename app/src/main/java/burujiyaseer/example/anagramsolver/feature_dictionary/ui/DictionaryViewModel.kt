package burujiyaseer.example.anagramsolver.feature_dictionary.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import burujiyaseer.example.anagramsolver.core.utils.Resource
import burujiyaseer.example.anagramsolver.feature_dictionary.domain.model.WordInfo
import burujiyaseer.example.anagramsolver.feature_dictionary.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "DictionaryViewModel"

@HiltViewModel
class DictionaryViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _searchedWordLiveData = MutableLiveData<Resource<List<String>>>()
    val searchedLiveData: LiveData<Resource<List<String>>>
        get() = _searchedWordLiveData

    fun onSearch(query: String) = viewModelScope.launch {
        _searchedWordLiveData.postValue(Resource.Loading)

        val searchedWord = withContext(Dispatchers.IO) { repository.getWordInfo(query) }
        val dictionaryResult = mutableListOf<String>()

        if (searchedWord is Resource.Success) {
            searchedWord.value.forEach { meaning ->
            dictionaryResult.add(meaning.word)
                meaning.meanings.forEach { def ->
                    dictionaryResult.add(def.partOfSpeech)
                    def.definitions.forEachIndexed { count,word ->
                        val definitionString = "${count+1}.  ${word.definition}"
                        dictionaryResult.add(definitionString)
                    }
                }
            }
            Log.d(TAG,"list is $dictionaryResult")
            _searchedWordLiveData.postValue(Resource.Success(dictionaryResult))
        } else _searchedWordLiveData.postValue(searchedWord as Resource.Failure)
    }
}