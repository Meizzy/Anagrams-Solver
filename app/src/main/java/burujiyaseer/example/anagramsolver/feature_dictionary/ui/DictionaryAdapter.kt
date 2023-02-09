package burujiyaseer.example.anagramsolver.feature_dictionary.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import burujiyaseer.example.anagramsolver.R
import burujiyaseer.example.anagramsolver.databinding.ItemDefBinding
import burujiyaseer.example.anagramsolver.databinding.ItemDefinitionsBinding

private const val TAG = "DictionaryAdapter"

class DictionaryAdapter(private var wordInfo: List<String>) : RecyclerView.Adapter<DictionaryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder {

        return when (viewType) {

            R.layout.item_def -> DictionaryViewHolder.TitleViewHolder(
                ItemDefBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> DictionaryViewHolder.ContentViewHolder(
                ItemDefinitionsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    fun setWords(data: List<String>) {
        wordInfo = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        if (wordInfo.isEmpty()) Log.d(TAG, "empty")
        else when (holder) {
            is DictionaryViewHolder.ContentViewHolder -> holder.bind(wordInfo[position])
            is DictionaryViewHolder.TitleViewHolder -> holder.bind(wordInfo[position])
        }
    }

    override fun getItemCount(): Int = if (wordInfo.isEmpty()) 1 else wordInfo.size

    override fun getItemViewType(position: Int): Int {
        return if (wordInfo.isEmpty()) R.layout.item_def
         else when (wordInfo[position] == wordInfo[0]) {
            true -> R.layout.item_def
            false -> R.layout.item_definitions
        }
    }
}