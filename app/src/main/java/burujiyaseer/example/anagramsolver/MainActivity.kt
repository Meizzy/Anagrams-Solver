package burujiyaseer.example.anagramsolver

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import burujiyaseer.example.anagramsolver.databinding.ActivityMainBinding
import burujiyaseer.example.anagramsolver.feature_anagram.ui.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate starts")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.includeContentMain.tvSearch.setOnClickListener {
            Log.d(TAG, "setOnClickListener: clicked")
            val intent = Intent(this, SearchActivity::class.java)

            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

    }
}