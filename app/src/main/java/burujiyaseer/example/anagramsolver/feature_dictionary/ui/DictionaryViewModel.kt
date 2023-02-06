package burujiyaseer.example.anagramsolver.feature_dictionary.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import burujiyaseer.example.anagramsolver.core.utils.Resource
import burujiyaseer.example.anagramsolver.feature_dictionary.domain.model.WordInfo
import burujiyaseer.example.anagramsolver.feature_dictionary.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class DictionaryViewModel (private val repository: Repository) : ViewModel() {

    private val delayTime = 500L

    private val _searchedWordLiveData = MutableLiveData<Resource<List<WordInfo>>>()
    val searchedLiveData: LiveData<Resource<List<WordInfo>>>
        get() = _searchedWordLiveData

    private var searchJob: Job? = null

    fun onSearch(query: String) {
        _searchedWordLiveData.postValue(Resource.Loading)
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(delayTime)

            val searchedWord = repository.getWordInfo(query).onEach { result ->
                when (result) {
                    is Resource.Failure -> _searchedWordLiveData.value = result
                    Resource.Loading -> _searchedWordLiveData.postValue(Resource.Loading)
                    is Resource.Success -> _searchedWordLiveData.value = result
                }
            }
            searchedWord.launchIn(this)
        }
    }
}