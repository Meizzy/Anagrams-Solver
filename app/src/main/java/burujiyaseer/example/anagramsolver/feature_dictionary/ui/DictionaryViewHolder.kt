package burujiyaseer.example.anagramsolver.feature_dictionary.ui

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import burujiyaseer.example.anagramsolver.databinding.ItemDefBinding
import burujiyaseer.example.anagramsolver.databinding.ItemDefinitionsBinding

sealed class DictionaryViewHolder( binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    class TitleViewHolder(private val binding: ItemDefBinding) : DictionaryViewHolder(binding) {
        fun bind(title: String) {
            binding.tvWord.text = title
        }
    }

    class ContentViewHolder(private val binding: ItemDefinitionsBinding) :
        DictionaryViewHolder(binding) {
        fun bind(words: String) {
            binding.tvWord.text = words
        }
    }
}