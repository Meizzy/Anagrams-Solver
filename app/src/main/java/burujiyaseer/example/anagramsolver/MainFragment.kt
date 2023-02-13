package burujiyaseer.example.anagramsolver

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import burujiyaseer.example.anagramsolver.core.base.BaseFragment
import burujiyaseer.example.anagramsolver.databinding.FragmentMainBinding
import burujiyaseer.example.anagramsolver.feature_anagram.ui.search.SearchActivity

class MainFragment: BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cvSearch.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(requireActivity()).toBundle())
        }
    }
}