package com.lynx.scoreblitz.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lynx.scoreblitz.databinding.StandingItemBinding
import com.lynx.scoreblitz.domain.model.StandingTotal

class StandingsAdapter : RecyclerView.Adapter<StandingsAdapter.StandingsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StandingsAdapter.StandingsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StandingItemBinding.inflate(inflater, parent, false)
        return StandingsViewHolder(binding)
    }

    class StandingsViewHolder(val binding: StandingItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: StandingTotal) {
            binding.standing = result
            binding.executePendingBindings()
        }

    }

    override fun onBindViewHolder(holder: StandingsAdapter.StandingsViewHolder, position: Int) {
        val standing = differ.currentList[position]
        standing?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffUtil = object : DiffUtil.ItemCallback<StandingTotal>() {
        override fun areItemsTheSame(oldItem: StandingTotal, newItem: StandingTotal): Boolean {
            return oldItem.league_key == newItem.league_key
        }

        override fun areContentsTheSame(oldItem: StandingTotal, newItem: StandingTotal): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

}