package burujiyaseer.example.anagramsolver.feature_dictionary.ui

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

//private const val TAG = "DictionaryViewModel"

@HiltViewModel
class DictionaryViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _searchedWordLiveData = MutableLiveData<Resource<List<WordInfo>>>()
    val searchedLiveData: LiveData<Resource<List<WordInfo>>>
        get() = _searchedWordLiveData

    fun onSearch(query: String) = viewModelScope.launch {
        _searchedWordLiveData.postValue(Resource.Loading)

        val searchedWord = withContext(Dispatchers.IO) { repository.getWordInfo(query) }

        if (searchedWord is Resource.Success) {
            _searchedWordLiveData.postValue(searchedWord)
        } else _searchedWordLiveData.postValue(searchedWord as Resource.Failure)
    }
}