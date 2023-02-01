package burujiyaseer.example.anagramsolver.feature_anagram.ui

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import burujiyaseer.example.anagramsolver.R
import burujiyaseer.example.anagramsolver.databinding.ItemContentBinding
import burujiyaseer.example.anagramsolver.databinding.ItemTitleBinding

sealed class RecyclerViewHolder(private val binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    class TitleViewHolder(private val binding: ItemTitleBinding) : RecyclerViewHolder(binding) {
        fun bind(title: String, context: Context) {
            binding.textViewTitle.text = context.getString(R.string.letter_word, title)
        }
    }

    class ContentViewHolder(private val binding: ItemContentBinding) : RecyclerViewHolder(binding) {
        fun bind(words: String) {
            binding.textViewTitle.text = words
        }
    }
}