package burujiyaseer.example.anagramsolver.feature_anagram.ui.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import burujiyaseer.example.anagramsolver.R
import burujiyaseer.example.anagramsolver.core.utils.Resource
import burujiyaseer.example.anagramsolver.core.utils.snackBar
import burujiyaseer.example.anagramsolver.core.utils.visible
import burujiyaseer.example.anagramsolver.databinding.ActivitySearchBinding
import burujiyaseer.example.anagramsolver.feature_anagram.data.network.AnagramWords
import burujiyaseer.example.anagramsolver.feature_anagram.ui.RecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "SearchActivity"

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private var searchView: SearchView? = null
    private lateinit var binding: ActivitySearchBinding
    private val recyclerViewAdapter = RecyclerViewAdapter(AnagramWords(ArrayList()))
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = recyclerViewAdapter
        }

        viewModel.anagramWordsLiveData.observe(this) {
            when (it) {
                is Resource.Failure -> {
                    binding.progressBar.visible(false)
                    if (it.isNetworkError) this.snackBar("Network error, please check your internet connection.")
                    else this.snackBar("Error ${it.errorCode}: ${it.errorBody}")
                }
                Resource.Loading -> binding.progressBar.visible(true)
                is Resource.Success -> {
                    binding.progressBar.visible(false)
                    recyclerViewAdapter.loadNewData(AnagramWords(it.value))
                }
            }
        }

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

}