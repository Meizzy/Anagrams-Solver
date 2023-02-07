package burujiyaseer.example.anagramsolver.feature_anagram.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import burujiyaseer.example.anagramsolver.core.base.BaseFragment
import burujiyaseer.example.anagramsolver.core.utils.Resource
import burujiyaseer.example.anagramsolver.core.utils.snackBar
import burujiyaseer.example.anagramsolver.core.utils.visible
import burujiyaseer.example.anagramsolver.databinding.FragmentAnagramBinding
import burujiyaseer.example.anagramsolver.feature_anagram.data.network.AnagramWords
import burujiyaseer.example.anagramsolver.feature_anagram.ui.RecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "AnagramFragment"

@AndroidEntryPoint
class AnagramFragment : BaseFragment<FragmentAnagramBinding>(FragmentAnagramBinding::inflate) {

    private val viewModel: SearchViewModel by activityViewModels()
    private val recyclerViewAdapter = RecyclerViewAdapter(AnagramWords(ArrayList()))
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated starts")
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            Log.d(TAG, "adapter starts")
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerViewAdapter
        }
        viewModel.anagramWordsLiveData.observe(viewLifecycleOwner) {
            Log.d(TAG, "live data starts")

            when (it) {
                is Resource.Failure -> {
                    binding.progressBar.visible(false)
                    if (it.isNetworkError) requireActivity().snackBar("Network error, please check your internet connection.")
                    else requireActivity().snackBar("Error ${it.errorCode}: ${it.errorBody}")
                }
                Resource.Loading -> binding.progressBar.visible(true)
                is Resource.Success -> {
                    binding.progressBar.visible(false)
                    recyclerViewAdapter.loadNewData(AnagramWords(it.value))
                }
            }
        }
        Log.d(TAG, "onViewCreated ends")

    }
}