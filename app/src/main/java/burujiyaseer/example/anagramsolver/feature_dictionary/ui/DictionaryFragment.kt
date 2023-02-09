package burujiyaseer.example.anagramsolver.feature_dictionary.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import burujiyaseer.example.anagramsolver.core.base.BaseFragment
import burujiyaseer.example.anagramsolver.core.utils.Resource
import burujiyaseer.example.anagramsolver.core.utils.snackBar
import burujiyaseer.example.anagramsolver.core.utils.visible
import burujiyaseer.example.anagramsolver.databinding.FragmentDictionaryBinding

private const val TAG = "DictionaryFragment"

class DictionaryFragment :
    BaseFragment<FragmentDictionaryBinding>(FragmentDictionaryBinding::inflate) {

    private val viewModel: DictionaryViewModel by activityViewModels()
    private val dictionaryAdapter = DictionaryAdapter(ArrayList())
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dictionaryRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = dictionaryAdapter
        }

        viewModel.searchedLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Failure -> {
                    Log.e(TAG, "failure with ${it.errorBody} and ${it.errorCode}")
                    binding.progressBar.visible(false)
                    if (it.isNetworkError) requireActivity().snackBar("Network error, please check your internet connection.")
                    else requireActivity().snackBar("Error ${it.errorCode}: word does not exist in dictionary")
                }
                Resource.Loading -> binding.progressBar.visible(true)
                is Resource.Success -> {
                    binding.progressBar.visible(false)
                    dictionaryAdapter.setWords(it.value)
                }
            }
        }
    }

}