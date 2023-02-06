package burujiyaseer.example.anagramsolver.feature_anagram.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import burujiyaseer.example.anagramsolver.feature_anagram.ui.search.AnagramFragment
import burujiyaseer.example.anagramsolver.feature_dictionary.ui.DictionaryFragment

class PagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
       return when(position){
           0 -> AnagramFragment()
           else -> DictionaryFragment()
       }
    }
}