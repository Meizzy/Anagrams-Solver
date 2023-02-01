package burujiyaseer.example.anagramsolver.feature_anagram.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import burujiyaseer.example.anagramsolver.R
import burujiyaseer.example.anagramsolver.databinding.ItemContentBinding
import burujiyaseer.example.anagramsolver.databinding.ItemTitleBinding
import burujiyaseer.example.anagramsolver.feature_anagram.data.network.AnagramWords

private const val TAG = "ADApter"

class RecyclerViewAdapter(private var words: AnagramWords) :
    RecyclerView.Adapter<RecyclerViewHolder>() {

    fun loadNewData(data: AnagramWords) {
        words = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        return when (viewType) {
            R.layout.item_content -> RecyclerViewHolder.ContentViewHolder(
                ItemContentBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> RecyclerViewHolder.TitleViewHolder(
                ItemTitleBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        }
    }


    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        if (words.all.isEmpty()) Log.d(TAG, "empty")
        else {
            when (holder) {
                is RecyclerViewHolder.ContentViewHolder -> holder.bind(words.all[position])
                is RecyclerViewHolder.TitleViewHolder -> holder.bind(
                    words.all[position], holder.itemView.context
                )
            }
        }
    }


    override fun getItemCount(): Int = if (words.all.isEmpty()) 1 else words.all.size

    override fun getItemViewType(position: Int): Int {
        return if (words.all.isEmpty()) R.layout.item_content //super.getItemViewType(position)
        else {
            when (words.all[position].toIntOrNull() != null) {
                true -> R.layout.item_title
                false -> R.layout.item_content
            }
        }
    }
}