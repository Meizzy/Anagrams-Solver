package burujiyaseer.example.anagramsolver.feature_anagram.ui.search


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import burujiyaseer.example.anagramsolver.core.utils.Resource
import burujiyaseer.example.anagramsolver.feature_anagram.data.network.AnagramWords
import burujiyaseer.example.anagramsolver.feature_anagram.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

//private const val TAG = "SearchViewModel"

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _anagramWordsLiveData = MutableLiveData<Resource<List<String>>>()
    val anagramWordsLiveData: LiveData<Resource<List<String>>>
        get() = _anagramWordsLiveData


    fun getAnagramsWordsList(query: String) = viewModelScope.launch {
        _anagramWordsLiveData.postValue(Resource.Loading)

        val deferredAnagramsWordsList = async { repository.getAnagramsWordsList(query) }

        val anagramWordsList = deferredAnagramsWordsList.await()

//        Log.d(TAG,"list of words found is $anagramWordsList" )
        val emptyWordsList = mutableListOf<AnagramWords>()
        val emptySortedWordsList = mutableListOf<String>()

        if (anagramWordsList is Resource.Success) {
            emptyWordsList.add(anagramWordsList.value)
            val sortedWords = emptyWordsList[0].all //.sortedBy { it.length }
            val returnWords = sortedWords.groupBy { it.length }
            for ((key, value) in returnWords) {
                if (key > 1) {
                    emptySortedWordsList.add(key.toString())
                    val formattedValues = value.toString().replace("[", "").replace("]", "")
                    emptySortedWordsList.add(formattedValues)
                }
            }
//            Log.d(TAG, "list is $emptySortedWordsList")
            _anagramWordsLiveData.postValue(Resource.Success(emptySortedWordsList))
        } else {
            _anagramWordsLiveData.postValue(anagramWordsList as Resource.Failure)
        }
    }
}