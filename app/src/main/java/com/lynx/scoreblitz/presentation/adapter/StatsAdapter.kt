package com.lynx.scoreblitz.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lynx.scoreblitz.databinding.H2hItemBinding
import com.lynx.scoreblitz.databinding.StatsProgressItemBinding
import com.lynx.scoreblitz.domain.model.Statistic

class StatsAdapter(private val clickOnStats: ((Statistic) -> Unit)? = null) :
    RecyclerView.Adapter<StatsAdapter.StatsViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StatsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StatsProgressItemBinding.inflate(inflater, parent, false)
        return StatsViewHolder(binding)
    }

    class StatsViewHolder(val binding: StatsProgressItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Statistic) {
            binding.stats = result
            binding.executePendingBindings()
            val homeProgress = binding.homeProgressBar
            val awayProgress = binding.awayProgressBar
            homeProgress.progress = parseProgressValue(result.home)
            awayProgress.progress = parseProgressValue(result.away)

            setProgressBarMax(homeProgress.progress, binding.homeProgressBar)
            setProgressBarMax(awayProgress.progress, binding.awayProgressBar)
        }

        private fun parseProgressValue(value: String?): Int {
            return if (value?.contains("%") == true) {
                value.replace("%", "").toIntOrNull() ?: 0
            } else {
                value?.toIntOrNull() ?: 0
            }
        }

        private fun setProgressBarMax(progress: Int, progressBar: ProgressBar) {
            progressBar.max = when (progress) {
                in 1..10 -> 10
                in 10..20 -> 25
                in 20..40 -> 40
                in 40..60 -> 60
                in 60..80 -> 80
                in 80..100 -> 150
                in 100..140 -> 160
                in 200..400 -> 400
                in 300..600 -> 600
                else -> { 100 }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        val stats = differ.currentList[position]
        stats?.let(holder::bind)

        if (stats.type == "Ball Possession"){
            holder.binding.type.text = "Possession(%)"
            holder.binding.homeProgressBar.progress = 100
            holder.binding.awayProgressBar.progress = 100
        }

        holder.itemView.setOnClickListener { _ ->
            clickOnStats?.let { it -> it(stats) }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Statistic>() {
        override fun areItemsTheSame(oldItem: Statistic, newItem: Statistic): Boolean {
            return oldItem.home == newItem.home
        }

        override fun areContentsTheSame(oldItem: Statistic, newItem: Statistic): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

}