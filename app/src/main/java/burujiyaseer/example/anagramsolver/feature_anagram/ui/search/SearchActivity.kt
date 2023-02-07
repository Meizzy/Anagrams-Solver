package burujiyaseer.example.anagramsolver.feature_anagram.ui.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import burujiyaseer.example.anagramsolver.R
import burujiyaseer.example.anagramsolver.databinding.ActivitySearchBinding
import burujiyaseer.example.anagramsolver.feature_anagram.ui.PagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "SearchActivity"

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private var searchView: SearchView? = null
    private lateinit var binding: ActivitySearchBinding
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tabLayoutSetup()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.search_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView?.setSearchableInfo(searchableInfo)

        searchView?.isIconified = false

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, ".onQueryTextSubmit: called")

                searchView?.clearFocus()
                if ((query != null) && query.isNotEmpty()) {
                    Log.d(TAG, "query is $query")
                    viewModel.getAnagramsWordsList(query)

                }
                return true
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        searchView?.setOnCloseListener {
            finish()
            false
        }

        Log.d(TAG, ".onCreateOptionsMenu: returning")
        return true
    }

    private fun tabLayoutSetup() {
        binding.includeSearch.viewPager.adapter = PagerAdapter(this)
        binding.includeSearch.tabLayout.tabIconTint = null
        TabLayoutMediator(
            binding.includeSearch.tabLayout,
            binding.includeSearch.viewPager
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.app_name)
                    tab.icon = AppCompatResources.getDrawable(this, R.drawable.my_logo)
                }
                1 -> {
                    tab.text = getString(R.string.dictionary)
                    tab.icon = AppCompatResources.getDrawable(this, R.drawable.green_logo)
                }
            }

        }.attach()
    }


}