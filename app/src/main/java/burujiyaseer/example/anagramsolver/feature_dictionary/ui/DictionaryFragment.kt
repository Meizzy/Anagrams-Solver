package burujiyaseer.example.anagramsolver.feature_dictionary.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import burujiyaseer.example.anagramsolver.core.base.BaseFragment
import burujiyaseer.example.anagramsolver.core.utils.Resource
import burujiyaseer.example.anagramsolver.core.utils.snackBar
import burujiyaseer.example.anagramsolver.core.utils.visible
import burujiyaseer.example.anagramsolver.databinding.FragmentDictionaryBinding

class DictionaryFragment :
    BaseFragment<FragmentDictionaryBinding>(FragmentDictionaryBinding::inflate) {

        private val viewModel: DictionaryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.searchedLiveData.observe(viewLifecycleOwner){
            when(it){
                is Resource.Failure -> {
                    if (it.isNetworkError) requireActivity().snackBar("Network error, please check your internet connection.")
                else requireActivity().snackBar("Error ${it.errorCode}: ${it.errorBody}")
            }
            Resource.Loading -> binding.progressBar.visible(true)
                is Resource.Success -> {
                    binding.title.text = it.value[0].word
                    binding.meaning.text = it.value[0].meanings[0].definitions[0].definition
                }
            }
        }
    }

}