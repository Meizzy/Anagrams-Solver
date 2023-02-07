package burujiyaseer.example.anagramsolver.feature_dictionary.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import burujiyaseer.example.anagramsolver.R
import burujiyaseer.example.anagramsolver.core.base.BaseFragment
import burujiyaseer.example.anagramsolver.core.utils.Resource
import burujiyaseer.example.anagramsolver.core.utils.snackBar
import burujiyaseer.example.anagramsolver.core.utils.visible
import burujiyaseer.example.anagramsolver.databinding.FragmentDictionaryBinding

private const val TAG = "DictionaryFragment"

class DictionaryFragment :
    BaseFragment<FragmentDictionaryBinding>(FragmentDictionaryBinding::inflate) {

    private val viewModel: DictionaryViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.searchedLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Failure -> {
                    Log.e(TAG, "failure with ${it.errorBody} and ${it.errorCode}")
                    binding.title.text = getString(R.string.error)
                    binding.meaning.text = getString(R.string.empty)
                    binding.progressBar.visible(false)
                    if (it.isNetworkError) requireActivity().snackBar("Network error, please check your internet connection.")
                    else requireActivity().snackBar("Error ${it.errorCode}: word does not exist in dictionary")
                }
                Resource.Loading -> binding.progressBar.visible(true)
                is Resource.Success -> {
                    Log.e(TAG, "success with ${it.value}")
                    binding.progressBar.visible(false)
                    binding.title.text = it.value[0].word
                    binding.meaning.text = it.value[0].meanings[0].definitions[0].definition
                }
            }
        }
    }

}