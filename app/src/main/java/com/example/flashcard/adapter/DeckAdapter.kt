package com.example.flashcard.adapter

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcard.api.modal.Word
import com.example.flashcard.databinding.ItemCardBinding

class DeckAdapter(private val data: ArrayList<Word>) : RecyclerView.Adapter<DeckAdapter.DeckViewHolder>() {

    inner class DeckViewHolder(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Word, index: Int) {
            binding.apply {
                txWord.text = data.word
                txMeaning.text = data.type
                txType.text = data.meaning
                txCount.text = "$index/${this@DeckAdapter.data.size}"
                viewHide.setOnClickListener {
                    ValueAnimator.ofFloat(1F, 0F).apply {
                        duration = 100
                        addUpdateListener {
                            val t = it.animatedValue as Float
                            binding.viewHide.alpha = t
                        }
                    }.start()
                    binding.textView2.isVisible = false
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckViewHolder {
        return DeckViewHolder(ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: DeckViewHolder, position: Int) {
        holder.bind(data[position], position + 1)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}