package burujiyaseer.example.anagramsolver.feature_wordle.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import burujiyaseer.example.anagramsolver.databinding.ContentWordleBinding

class WordleActivity : AppCompatActivity() {
    private lateinit var binding: ContentWordleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ContentWordleBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}